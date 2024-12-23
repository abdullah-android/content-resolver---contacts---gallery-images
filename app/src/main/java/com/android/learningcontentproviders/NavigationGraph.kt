package com.android.learningcontentproviders

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.learningcontentproviders.contacts_related.ContactsScreen
import com.android.learningcontentproviders.contacts_related.ContactsViewModel
import com.android.learningcontentproviders.gallery_images_related.GalleryImagesScreen
import com.android.learningcontentproviders.gallery_images_related.GalleryImagesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
) {

    NavHost(
        navController = navHostController,
        startDestination = NavDestinations.MAIN_SCREEN,
    ) {

        composable<NavDestinations.MAIN_SCREEN>() {
            MainScreen(
                navHostController = navHostController
            )
        }

        composable<NavDestinations.CONTACTS_SCREEN>() {
            val contactsViewModel = koinViewModel<ContactsViewModel>()

            ContactsScreen(
                navHostController = navHostController,
                contactsViewModel = contactsViewModel
            )
        }

        composable<NavDestinations.GALLERY_IMAGES_SCREEN>() {
            val galleryImagesViewModel = koinViewModel<GalleryImagesViewModel>()

            GalleryImagesScreen(
                navHostController = navHostController,
                galleryImagesViewModel = galleryImagesViewModel
            )
        }

    }

}