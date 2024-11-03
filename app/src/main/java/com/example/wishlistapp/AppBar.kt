package com.example.wishlistapp

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


//defines a custom bar
@Composable
fun AppBarView(
    title: String,
    // Callback function for back navigation button click that does nothing
    //() - no parameter || Unit - returns nothing
    //callback fnn - a fnn passed as na arguement to another fnn with the intention of being called
    onBackNavClicked: () -> Unit = {},
    navController: NavController
) {
    // Define the navigation icon as a lambda function of type that is a nullable composable function
    //passing @Composable as a parameter to another fnn, allowing the caller to provide the
    // ..implementation details of a part of the UI -enables creating reusable nad customizable ui components
    //in navigationIcon @Composable is used to dynamically decide what icon should be displayed in the app bar based on title condition
    val navigationIcon: (@Composable () -> Unit)? = {
        //if title doesnt contain the substring "Wishlist" then only display arrow
        if (!title.contains("WISHLIST")) {
            //onNavBackClicked() - to navigate back to the prev screen
            IconButton(onClick = { onBackNavClicked() }) {
                Icon(
                    //tint - color of an icon or drawable
                    //contentColor - setting color for a text or icon within a composable fnn
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        } else {
            null
        }
    }
    val settingsIcon: (@Composable () -> Unit) = {
        IconButton(onClick = {
            navController.navigate(Screen.SettingsScreen.route)

        }) {
            Icon(
                imageVector = Icons.Filled.Settings,
                tint = Color.White,
                contentDescription = "Settings"
            )
        }
    }

    // Define the top app bar
    TopAppBar(
        // Title as a composable function
        title = {
            Text(
                text = title,
                // Retrieving a color value from the resource folder (colors.xml)
                color = colorResource(id = R.color.white),
                modifier = Modifier
                    .padding(start = 4.dp)
                    // Constrain the height of the text within a specified range
                    .heightIn(max = 24.dp)
            )
        },
        // Adding a shadow effect
        elevation = 3.dp,
        backgroundColor = Color.Black,
        //setting up the navigation icon
        navigationIcon = navigationIcon,
        actions = {
            settingsIcon()
        }
    )
}

