package com.android.learningcontentproviders.gallery_images_related

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GalleryImagesViewModel : ViewModel() {


    private val _galleryImagesList = MutableStateFlow<List<GalleryImagesModel>>(emptyList())
    val galleryImagesList = _galleryImagesList.asStateFlow()


    @SuppressLint("Range")
    fun getGalleryImagesList(
        context: Context,
    ) {

        try {

            val contentResolver = context.contentResolver

            val thisGalleryImagesList = mutableStateListOf<GalleryImagesModel>()


            val projection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                arrayOf(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media._ID,
                )
            else arrayOf(
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media._ID,
            )

            val selection = null
            val selectionArgs = null


            val order = "${MediaStore.Images.Media.DATE_ADDED} DESC"


            contentResolver
                .query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    order
                )
                .use { cursor ->

                    when (cursor?.count) {
                        null -> {}

                        0 -> {}

                        else -> {

                            val columnName =
                                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

                            val columnId = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

                            while (cursor.moveToNext()) {

                                val id = cursor.getLong(columnId)
                                val name = cursor.getString(columnName)

                                val imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                                    ContentUris.withAppendedId(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        id
                                    ) else cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))

                                val galleryImagesModel = GalleryImagesModel(
                                    imageName = name,
                                    imageUrl = imageUri.toString(),
                                    imageId = id,
                                )

                                thisGalleryImagesList.add(galleryImagesModel)

                            }

                            _galleryImagesList.value = thisGalleryImagesList

                        }
                    }

                }
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.localizedMessage ?: "")
            Toast.makeText(context, "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCleared() {
        super.onCleared()
        _galleryImagesList.value = emptyList()
    }


}