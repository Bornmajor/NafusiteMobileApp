package com.majasociet.nafusitemobileapp.features.profile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.majasociet.nafusitemobileapp.R
import com.majasociet.nafusitemobileapp.shared.constants.AppConstants

/**
 * Profile avatar composable display profile image for user
 * @param imageUrl - url of the image
 * @param imagePreviewUrl - url of the image preview
 * @param onChangeAvatar - callback to change avatar
 */
@Composable
fun ProfileAvatar(
    imageUrl: String = AppConstants.DEFAULT_AVATAR_URL,
    imagePreviewUrl:String? = null,
    onChangeAvatar: (() -> Unit)? = null,
) {
    val resolvedImageUrl = imageUrl.ifBlank { AppConstants.DEFAULT_AVATAR_URL }

    Box(
        modifier = Modifier.size(100.dp)
    ) {

            AsyncImage(
                model = if(imagePreviewUrl.isNullOrBlank()) resolvedImageUrl else imagePreviewUrl,
                contentDescription = "Profile Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

        if(onChangeAvatar != null){
            // The Overlapping Action Button
            IconButton(
                onClick = {
                    onChangeAvatar()
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 4.dp, y = 4.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .border(2.dp, Color.White, CircleShape) // White ring cuts it cleanly from the image
            ) {
                Icon(
                    painter = painterResource(R.drawable.photo_camera_24px),
                    contentDescription = "Edit Profile Picture",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

    }


}
