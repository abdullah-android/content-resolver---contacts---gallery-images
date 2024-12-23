package com.android.learningcontentproviders.contacts_related

data class ContactsModel(
    val name: String? = "",
    val number: String? = "",
    val photoUrl: String? = "",
) {
    constructor() : this("", "", "")
}
