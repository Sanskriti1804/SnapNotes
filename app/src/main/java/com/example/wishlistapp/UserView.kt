package com.example.wishlistapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wishlistapp.ui.theme.green
import com.example.wishlistapp.ui.theme.lightBlue
import com.example.wishlistapp.ui.theme.orange
import com.example.wishlistapp.ui.theme.yellow

@Composable
fun UserView(viewModel: WishViewModel, navController: NavController){
//fun UserView( navController: NavController){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.main_page),
            contentDescription = "User View Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize())

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 48.dp)
                .align(Alignment.BottomCenter)
        ) {
            Button(
                onClick = {
                navController.navigate(Screen.SignupScreen.route)
            },
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = green)

            ) {
                Text(text = "Sign Up",
                    modifier = Modifier.padding(4.dp),
                    fontSize = 22.sp
                )
            }
            Button(onClick = {
                navController.navigate(Screen.SigninScreen.route) },
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = lightBlue )

            ) {
                Text(text = "Sign In",
                    modifier = Modifier.padding(4.dp),
                    fontSize = 22.sp)

            }
        }

    }

}

/*
@Preview(showBackground = true)
@Composable
fun Page1Preview(){
    //UserView(viewModel = viewModel(), navController = rememberNavController())
    UserView( navController = rememberNavController())
}

*/