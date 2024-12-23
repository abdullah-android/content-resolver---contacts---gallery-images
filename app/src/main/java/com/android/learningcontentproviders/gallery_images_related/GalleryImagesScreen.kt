package com.android.learningcontentproviders.gallery_images_related

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryImagesScreen(
    navHostController: NavHostController,
    galleryImagesViewModel: GalleryImagesViewModel,
) {

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                galleryImagesViewModel.getGalleryImagesList(context)
            }
        }
    )


    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    }

    val galleryImagesList by galleryImagesViewModel.galleryImagesList.collectAsStateWithLifecycle()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Gallery Images") },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Icon for Navigate Back"
                        )
                    }
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
        ) {
            items(galleryImagesList.size) { index ->
                val item = galleryImagesList[index]

                GalleryImagesListItem(
                    item = item
                )
            }
        }
    }

}

@Composable
fun GalleryImagesListItem(
    item: GalleryImagesModel,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 20.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 1.dp,
                    color = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray,
                    shape = RoundedCornerShape(10.dp)
                ),
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = "Gallery Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Text(
                text = item.imageName ?: "",
                modifier = Modifier
                    .padding(all = 5.dp)
            )
        }
    }
}