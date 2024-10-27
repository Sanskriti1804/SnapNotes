package com.example.wishlistapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.Navigation

//change 1
data class navItems(
    val title : String,
    val selectedIcon : ImageVector,
    val unselectedIcons : ImageVector,
    val badgeCount : Int? = null
)

fun NavigationItems(){
    listOf<navItems>(
        navItems(
        title = "All",
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcons = Icons.Outlined.Favorite,
        badgeCount = null
        ),
        navItems(
            title = "All",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcons = Icons.Outlined.Favorite,
            badgeCount = null
        ),
        navItems(
            title = "All",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcons = Icons.Outlined.Favorite,
            badgeCount = null
        )
    )
}