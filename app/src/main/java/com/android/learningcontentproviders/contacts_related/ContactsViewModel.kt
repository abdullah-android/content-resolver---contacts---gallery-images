package com.android.learningcontentproviders.contacts_related

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.io.use

class ContactsViewModel : ViewModel() {

    private val _contactsList = MutableStateFlow<List<ContactsModel>>(emptyList())
    val contactsList = _contactsList.asStateFlow()


    fun getContactsList(contentResolver: ContentResolver) {

        try {

            var thisContactsList = mutableStateListOf<ContactsModel>()

            val order = "${ContactsContract.Contacts.DISPLAY_NAME} DESC"

            // Projection is What You want to related to query
            val projection: Array<String>? = arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
            )
            val selection = "${ContactsContract.Contacts.HAS_PHONE_NUMBER} = ?"
            var selectionArgs: Array<String>? = arrayOf("1") // this will replace the '?'

            // for one or more selectionArgs do this:-

            // val selection = "${ContactsContract.Contacts._ID} LIKE (?, ?)"
            // val selectionAgs = arrayOf(1, 2) // where the contact id 1 or 2 it will get that

            contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                order
            ).use { cursor ->

                when (cursor?.count) {
                    null -> {}
                    0 -> {}

                    else -> {

                        val columnName =
                            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

                        val columnPhotoUri =
                            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)

                        val columnNumber =
                            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)


                        while (cursor.moveToNext()) {
                            val name = cursor.getString(columnName) ?: ""
                            val photoUri = cursor.getString(columnPhotoUri) ?: ""
                            val number = cursor.getString(columnNumber) ?: ""

                            val contact = ContactsModel(
                                name = name,
                                number = number,
                                photoUrl = photoUri
                            )

                            thisContactsList.add(contact)
                        }

                        println("Contacts: fetchContacts(): $thisContactsList")
                        _contactsList.value = thisContactsList
                    }

                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun getContactById(
        contactId: String,
        contentResolver: ContentResolver
    ) {
        try {

            val thisContactsList = mutableStateListOf<ContactsModel>()

            val phoneCursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
                ),
                "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                arrayOf(contactId),
                null
            )

            phoneCursor
                .use { phoneCursor ->
                    if (
                        phoneCursor?.moveToNext()!!
                        && phoneCursor.count != 0
                    ) {
                        val columnName =
                            phoneCursor.getColumnIndex(
                                ContactsContract.Contacts.DISPLAY_NAME
                            )

                        val columnPhotoUri =
                            phoneCursor.getColumnIndex(
                                ContactsContract.Contacts.PHOTO_URI
                            )

                        val columnNumber =
                            phoneCursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            )


                        if (phoneCursor.moveToFirst()) {

                            val name =
                                phoneCursor.getString(columnName)
                            val photoUrl =
                                phoneCursor.getString(columnPhotoUri)
                            val number =
                                phoneCursor.getString(columnNumber) ?: "Failed to get number or he don't have"

                            val contact = ContactsModel(
                                name = name,
                                number = number,
                                photoUrl = photoUrl
                            )

                            thisContactsList.add(contact)
                        }
                        _contactsList.value = thisContactsList
                    } else {

                    }
                }

        } catch (e: Exception) {
            e.printStackTrace()
            println("Error: ${e.localizedMessage}")
        }
    }



    fun getContactByContactUri(
        contactUri: Uri,
        contentResolver: ContentResolver
    ) {
        try {

            contentResolver
                .query(
                    contactUri,
                    arrayOf(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER,
                        ContactsContract.Contacts._ID,
                    ),
                    null,
                    null,
                    null
                )
                .use { cursor ->
                    if (cursor?.moveToNext()!!) {

                        when (cursor.count) {
                            0 -> {}

                            else -> {

                                val columnHasNumber =
                                    cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)

                                val columnId = cursor.getColumnIndex(ContactsContract.Contacts._ID)

                                if (cursor.moveToFirst()) {

                                    val hasNumber = cursor.getInt(columnHasNumber)
                                    val contactId = cursor.getString(columnId)

                                    if (hasNumber > 0) {

                                        getContactById(
                                            contactId = contactId,
                                            contentResolver = contentResolver
                                        )

                                    }
                                }
                            }

                        }
                    } else {

                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error: ${e.localizedMessage}")
        }
    }


    override fun onCleared() {
        super.onCleared()
        _contactsList.value = emptyList()
    }


}