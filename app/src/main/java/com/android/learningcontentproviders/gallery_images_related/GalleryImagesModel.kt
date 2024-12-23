package com.android.learningcontentproviders.gallery_images_related

data class GalleryImagesModel(
    val imageName: String? = "",
    val imageUrl: String? = "",
    val imageId: Long? = 0L,
) {
    constructor(): this(
        imageName = "",
        imageUrl = "",
        imageId = 0L,
    )
}
