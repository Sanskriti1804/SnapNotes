package com.example.wishlistapp

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wishlistapp.ui.theme.orange
import com.example.wishlistapp.ui.theme.yellow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class LoginAuthViewModel : ViewModel(){
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _userState = MutableStateFlow<FirebaseUser?>(null)
    val userState : StateFlow<FirebaseUser?> = _userState.asStateFlow()

    val _authError = MutableStateFlow<String?>(null)
    val authError : StateFlow<String?> = _authError.asStateFlow()

    fun validateForm(email : String, password : String) : Boolean{
        return when{
            email.isBlank()->{
                _authError.value = "Enter email"
                false
            }
            password.isBlank()->{
                _authError.value = "Enter password"
                false
            }
            else -> true
        }
    }

    fun loginUser(email : String, password: String){
        if (validateForm(email, password)){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{task ->
                    if (task.isSuccessful){
                        _userState.value = auth.currentUser
                    }
                    else{
                        _authError.value = "Login unsuccessful : ${task.exception?.message}"
                    }
                }
        }
    }
}

@Composable
fun SigninView(viewModel: LoginAuthViewModel, navController: NavController) {

    isUserAlreadySignIn(navController)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SigninCard(navController = navController,viewModel = viewModel)
    }
}

fun isUserAlreadySignIn(navController: NavController) {
    if(FirebaseAuth.getInstance().currentUser != null){
        navController.navigate(Screen.HomeScreen.route)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SigninCard( navController: NavController,  viewModel : LoginAuthViewModel) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("")}
    val authError by  viewModel.authError.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.black_bg),
            contentDescription = "Black background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            Card(
                modifier = Modifier.padding(32.dp),
                elevation = CardDefaults.cardElevation(16.dp),
                colors = CardDefaults.cardColors(containerColor = yellow)
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = emailState.value,
                        onValueChange = { emailState.value = it },
                        colors = TextFieldDefaults.textFieldColors(containerColor = yellow),
                        label = { Text(text = "Email") },
                        placeholder = { Text(text = "Enter your email") }
                    )
                    TextField(
                        value = passwordState.value,
                        onValueChange = { passwordState.value = it },
                        colors = TextFieldDefaults.textFieldColors(containerColor = yellow),
                        label = { Text(text = "Password") },
                        placeholder = { Text(text = "Enter your password") },
                        visualTransformation = PasswordVisualTransformation()
                    )

                }
            }
            Button(onClick = {
                viewModel.loginUser(emailState.value, passwordState.value)
                if (viewModel.userState.value != null){
                    navController.navigate(Screen.HomeScreen.route)
                } },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = yellow),
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
            ) {
                Text(text = "Sign in",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

    }
}

