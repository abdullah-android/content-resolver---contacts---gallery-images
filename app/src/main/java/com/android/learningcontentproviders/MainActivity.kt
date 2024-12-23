package com.android.learningcontentproviders

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.android.learningcontentproviders.contacts_related.ContactsScreen
import com.android.learningcontentproviders.contacts_related.ContactsViewModel
import com.android.learningcontentproviders.ui.theme.LearningContentProvidersTheme

class MainActivity : ComponentActivity() {

    private val contactsViewModel by viewModels<ContactsViewModel>()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()

            LearningContentProvidersTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    NavigationGraph(
                        navHostController = navHostController,
                    )
                }
            }
        }
    }
}