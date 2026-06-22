package com.majasociet.nafusitemobileapp.features.auth.ui.components

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.majasociet.nafusitemobileapp.R
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@OptIn(UnstableApi::class)
@Composable
fun CustomVideoPlayer(
    localVideoUri: Uri,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Initialize and remember the ExoPlayer instance allocation
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(localVideoUri)
            setMediaItem(mediaItem)
            prepare()
        }
    }

    var isPlaying by remember { mutableStateOf(false) }
    var showControls by remember { mutableStateOf(true) }

    // Listen to native player state changes to keep our Compose UI synced
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release() // CRITICAL: Releases hardware decoders when leaving the screen
        }
    }

    // Auto-hide controls after 3 seconds when playing
    LaunchedEffect(showControls, isPlaying) {
        if (showControls && isPlaying) {
            delay(3000.milliseconds) // Hide after 3 seconds
            showControls = false
        }
    }

    // 3. The Visual Layout Structure
    Box(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // No ripple effect
            ) {
                // Tapping the video shows controls
                showControls = true
            }
    ) {
        // Render the raw native Video Surface via an AndroidView bridge
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Play/Pause button - only show when controls are visible OR when paused
        if (showControls || !isPlaying) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        if (isPlaying) {
                            exoPlayer.pause()
                            showControls = true // Keep showing when paused
                        } else {
                            exoPlayer.play()
                            showControls = true // Show briefly when starting
                        }
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        painter = painterResource(
                            if (isPlaying) R.drawable.baseline_pause_24
                            else R.drawable.baseline_play_arrow_24
                        ),
                        contentDescription = "Playback Controller Action Toggle"
                    )
                }
            }
        }
    }
}