package com.example.wishlistapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AppInfo(
    viewModel: WishViewModel,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .padding(12.dp)
            .fillMaxSize()
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, bottom = 60.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "APP INFO",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 44.sp,
                    letterSpacing = 1.sp,
                    modifier = Modifier
                        .border(5.dp, Color.Black, RoundedCornerShape(10.dp))
                        .padding(start = 25.dp, end = 25.dp, top = 5.dp, bottom = 5.dp)
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "App Name :",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Snap Notes",
                    fontSize = 22.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Version :",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = "1.0.0",
                    fontSize = 22.sp,
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                )
            }
        }

        item {
            Divider(
                color = Color.Gray,
                thickness = 2.dp,
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
            )
            Text(
                text = "Description :",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "SnapNotes is a modern, intuitive app designed to help you organize your thoughts and keep track of important information effortlessly. With its sleek, user-friendly interface, SnapNotes lets you quickly create and manage notes, add tags, mark favorites, and search with ease. Ideal for students, professionals, and anyone who loves organized, on-the-go note-taking. Enjoy a secure experience with Firebase authentication for seamless sign-in and app lock for enhanced privacy.",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 1.dp)
            )
            Divider(
                color = Color.Gray,
                thickness = 2.dp,
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
            )
            Text(
                text = "Key Features:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "👉🏿Create and Manage Notes:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "Easily add a title and description to each note to keep everything organized.",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "👉🏿Mark Favorites:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "Highlight your favorite notes for quick access and better organization.",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "👉🏿Tagging System:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "Add tags to categorize your notes and improve searchability.",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "👉🏿Search Notes: ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "Find notes quickly with the powerful search feature by title, tag, or description.",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "👉🏿App Lock:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "Keep your notes secure with the built-in app lock feature, allowing you to set a password or use biometric authentication.",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "👉🏿Firebase Authentication: ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "Sign in and manage your account securely with Firebase.",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "👉🏿Modern and Unique Design: ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = " A sleek, minimalistic interface that provides a visually pleasing user experience.",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 1.dp)
            )
        }
        item {
            Divider(
                color = Color.Gray,
                thickness = 2.dp,
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
            )
            Text(
                text = "Usage Tips :",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "👉🏿Organize with Tags:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "Use tags to sort and quickly access specific types of notes.",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "👉🏿Use Favorites:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "Mark important notes as favorites to easily find them later.",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "👉🏿Enable App Lock:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "Secure your notes by enabling app lock through the settings.",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "👉🏿Use Search for Quick Access:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "Search by keywords to quickly locate notes.",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 1.dp)
            )
        }

        item {
            Divider(
                color = Color.Gray,
                thickness = 2.dp,
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
            )
            Text(
                text = "Permissions Required :",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "👉🏿Storage Access:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "To save and retrieve your notes locally.",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "👉🏿Gallery Access ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "To select a profile photo from your device’s gallery.",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 1.dp)
            )

        }
        item {
            Divider(
                color = Color.Gray,
                thickness = 2.dp,
                modifier = Modifier.padding(top = 20.dp, bottom = 30.dp)
            )
            Text(
                text = "Contact Support :",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "If you encounter any issues or have questions, please reach out to our support team:",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "Email",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = "support@snapnotes.com",
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 1.dp),
                color = Color.Blue
            )
        }

    }
}
