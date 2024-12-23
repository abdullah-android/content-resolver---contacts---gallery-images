package com.android.learningcontentproviders

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navHostController: NavHostController
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Main") },
            )
        },
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
       Box(
           contentAlignment = Alignment.Center,
           modifier = Modifier
               .fillMaxSize()
               .padding(paddingValues = innerPadding)
       ) {
           Column(
               verticalArrangement = Arrangement.spacedBy(20.dp),
               horizontalAlignment = Alignment.CenterHorizontally,
               modifier = Modifier
                   .fillMaxWidth()
           ) {
               Button(onClick = {
                   navHostController
                       .navigate(NavDestinations.CONTACTS_SCREEN)
               }) { Text(text = "Contacts") }

               Button(onClick = {
                   navHostController
                       .navigate(NavDestinations.GALLERY_IMAGES_SCREEN)
               }) { Text(text = "Gallery Images") }
           }
       }
    }

}