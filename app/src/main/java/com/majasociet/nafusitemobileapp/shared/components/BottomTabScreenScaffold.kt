package com.majasociet.nafusitemobileapp.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.majasociet.nafusitemobileapp.R


/**
 * This is the actual composable screen with the scaffold
 */
@Composable
fun BottomTabScreenScaffold(
    navigateToProfile:() -> Unit,
    navigateToSearch:() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
){
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                        onClick = navigateToProfile
                        ) {
                    Icon(
                        modifier= Modifier.size(30.dp),
                        painter = painterResource(R.drawable.baseline_person_24),
                        contentDescription = "Profile"
                    )
                }
                IconButton(
                    onClick = navigateToSearch
                ) {
                    Icon(
                        modifier= Modifier.size(30.dp),
                        painter = painterResource(R.drawable.baseline_search_24),
                        contentDescription = "Search"
                    )
                }

            }
        },
        content = { innerPadding -> Box(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ){
            content()
        }

        }
    )

}

