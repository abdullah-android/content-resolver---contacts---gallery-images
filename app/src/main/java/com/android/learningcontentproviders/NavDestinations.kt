package com.android.learningcontentproviders

import kotlinx.serialization.Serializable

sealed class NavDestinations {

    @Serializable
    data object MAIN_SCREEN: NavDestinations()

    @Serializable
    data object CONTACTS_SCREEN: NavDestinations()

    @Serializable
    data object GALLERY_IMAGES_SCREEN: NavDestinations()

}