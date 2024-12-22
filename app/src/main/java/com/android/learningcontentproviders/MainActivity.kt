package com.android.learningcontentproviders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.android.learningcontentproviders.contacts_related.ContactsScreen
import com.android.learningcontentproviders.contacts_related.ContactsViewModel
import com.android.learningcontentproviders.ui.theme.LearningContentProvidersTheme

class MainActivity : ComponentActivity() {

    private val contactsViewModel by viewModels<ContactsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearningContentProvidersTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ContactsScreen(
                        contactsViewModel = contactsViewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = innerPadding)
                    )
                }
            }
        }
    }
}