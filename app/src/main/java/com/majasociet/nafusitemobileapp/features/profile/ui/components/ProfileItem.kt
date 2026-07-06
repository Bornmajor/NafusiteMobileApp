package com.majasociet.nafusitemobileapp.features.profile.ui.components

import android.widget.Space
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.majasociet.nafusitemobileapp.ui.theme.AppTheme

@Composable
fun ProfileItem(
    icon:Int,
    title:String,
    action:@Composable () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(AppTheme.spacing.medium))
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleMedium
        )

        action()

    }
}