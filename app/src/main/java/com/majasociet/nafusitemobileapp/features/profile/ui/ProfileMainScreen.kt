package com.majasociet.nafusitemobileapp.features.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.majasociet.nafusitemobileapp.R
import com.majasociet.nafusitemobileapp.features.profile.ui.components.ProfileAvatar
import com.majasociet.nafusitemobileapp.features.profile.ui.components.ProfileItem
import com.majasociet.nafusitemobileapp.features.profile.ui.components.ProfileScaffold
import com.majasociet.nafusitemobileapp.features.profile.ui.viewmodel.ProfileViewModel
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme

@Immutable
data class ProfileMainScreenContentState(
    val profileImageUrl: String,
    val firstName: String?,
    val email: String?,
    val onEditProfile: () -> Unit,
    val onLogout: () -> Unit
)

/**
 * Stateful wrapper: reads state from ViewModel and maps to UI state.
 */
@Composable
fun ProfileMainScreen(
    navigateEditProfile: () -> Unit,
    logout: () -> Unit,
    profileViewModel: ProfileViewModel,
    navigateBack: () -> Unit,
    navigateToSearch: () -> Unit
) {
    val profileState = profileViewModel.profileState.collectAsStateWithLifecycle().value
    val user = profileState.user

    val contentState = ProfileMainScreenContentState(
        profileImageUrl = user?.profileImgUrl.orEmpty(),
        firstName = user?.firstName,
        email = user?.email,
        onEditProfile = navigateEditProfile,
        onLogout = logout
    )

    ProfileScaffold(
        navigateBack = navigateBack,
        navigateToSearch = navigateToSearch,
        content = {
            ProfileMainScreenContent(state = contentState)
        }
    )
}

/**
 * Stateless layout: only depends on provided state.
 * @param state - profile main screen content state
 */
@Composable
fun ProfileMainScreenContent(
    state: ProfileMainScreenContentState
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProfileAvatar(imageUrl = state.profileImageUrl)

        Spacer(modifier = Modifier.padding(AppTheme.spacing.medium))

        state.firstName?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }

        state.email?.let {
            Text(text = it)
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.large))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    color = Color(0xFFF1F1F1),
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 2.dp,
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFF1F1F1)
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = AppTheme.spacing.small,
                        vertical = AppTheme.spacing.medium
                    ),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium)
            ) {
                ProfileItem(
                    icon = R.drawable.baseline_person_24,
                    title = "Edit profile",
                    action = {
                        CaretIconButton(onClick = state.onEditProfile)
                    }
                )

                ProfileItem(
                    icon = R.drawable.baseline_bedtime_24,
                    title = "Dark mode",
                    action = {
                        // TODO: reusable switch
                    }
                )

                ProfileItem(
                    icon = R.drawable.baseline_exit_to_app_24,
                    title = "Logout",
                    action = {
                        CaretIconButton(onClick = state.onLogout)
                    }
                )
            }
        }
    }
}

/**
 * Caret icon button
 * @param onClick - on click
 */
@Composable
fun CaretIconButton(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(R.drawable.arrow_forward_ios_24px),
            contentDescription = "Arrow Forward"
        )
    }
}