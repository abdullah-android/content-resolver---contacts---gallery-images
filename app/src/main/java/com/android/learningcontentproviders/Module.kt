package com.android.learningcontentproviders

import com.android.learningcontentproviders.contacts_related.ContactsViewModel
import com.android.learningcontentproviders.gallery_images_related.GalleryImagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val MainModule = module {

    viewModelOf(::ContactsViewModel)

    viewModelOf(::GalleryImagesViewModel)

}