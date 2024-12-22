package com.android.learningcontentproviders.contacts_related

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.android.learningcontentproviders.common.startsWithAlphabet
import kotlin.let
import kotlin.text.first
import kotlin.text.isLetter
import kotlin.text.isLowerCase
import kotlin.text.isNullOrEmpty
import kotlin.text.isUpperCase
import kotlin.toString

@Composable
fun ContactsScreen(
    contactsViewModel: ContactsViewModel,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val contactsPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact(),
        onResult = { contactUri ->
            contactUri?.let {
                contactsViewModel.getContactsList(context)

                // if You want to get the Details of selected Contact:

//                contactsViewModel.getContactByContactUri(
//                    contactUri = contactUri,
//                    context = context
//                )
            }
        }
    )

    val contactsPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                contactsPickerLauncher.launch()
            }
        }
    )

    LaunchedEffect(Unit) {
        contactsPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
    }

    val contactsList by contactsViewModel.contactsList.collectAsState()

    LazyColumn(
        modifier = modifier
    ) {
        items(contactsList.size) { index ->
            val item = contactsList[index]

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 20.dp)
                    .clickable(
                        enabled = true
                    ) {

                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (item.photoUrl.isNullOrEmpty()) {
                                if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                            } else Color.Transparent
                        ),
                        shape = CircleShape,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .clip(CircleShape)
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {

                            val name = item.name ?: ""

                            if (item.photoUrl.isNullOrEmpty()) {
                                if (name.startsWithAlphabet()) {
                                    Text(
                                        text = (item.name).toString().first().toString(),
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            } else {
                                AsyncImage(
                                    model = item.photoUrl,
                                    contentDescription = "Contact's Image",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape)
                                )
                            }
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Text(
                            text = "${item.name}",
                        )

                        Text(
                            text = "${item.number}",
                        )
                    }
                }
            }

        }
    }


}