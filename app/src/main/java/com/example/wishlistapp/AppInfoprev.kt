package com.example.wishlistapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AppInfoPrev(
    viewModel: WishViewModel,
    navController: NavController
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(id = R.drawable.app_icon),
                    contentDescription = "App Icon",
//        alignment = Alignment.TopStart,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp)
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = "Snap",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Notes",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Snap Notes",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 10.dp),
                color = Color.DarkGray
            )
            Text(
                text = "Turn Your Thoughts",
                letterSpacing = 1.sp,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "into SnapNotes",
                fontSize = 32.sp,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "in a Snap!",
                fontSize = 32.sp,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(Screen.InfoScreen.route) }
                .padding(14.dp, bottom = 25.dp)
                .align(Alignment.BottomCenter)
        ){
            Text(
                text = "App Info",
                color = Color.Gray,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
            )
            Image(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "go icon",
                modifier = Modifier
                    .size(46.dp)
                    .padding(start = 22.dp, top = 12.dp),
                colorFilter = ColorFilter.tint(Color.Gray)
            )
        }
    }
}

