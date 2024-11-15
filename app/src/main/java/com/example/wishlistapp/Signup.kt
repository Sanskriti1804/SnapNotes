package com.example.wishlistapp

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.wishlistapp.ui.theme.orange
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

//@Composable - should not be marked - interacting w fb
class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _userState = MutableStateFlow<FirebaseUser?>(null)
    val userState: StateFlow<FirebaseUser?> = _userState.asStateFlow()

    // Add _emailState
    private val _emailState = MutableStateFlow<String?>(null)
    val emailState: StateFlow<String?> = _emailState.asStateFlow()

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            name.isBlank() -> {
                _authError.value = "Enter your name"
                false
            }

            email.isBlank() -> {
                _authError.value = "Enter your name"
                false
            }

            password.isBlank() -> {
                _authError.value = "Enter your name"
                false
            }
            else -> true
        }
    }

        fun registerUser(name: String, email: String, password: String) {
        if (validateForm(name, email, password)) {
            viewModelScope.launch {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _userState.value = auth.currentUser
                        }
                        else{
                            _authError.value =
                                "Registration failed : ${task.exception?.message}"
                            }
                        }
            }
        }
        }
    fun saveUserEmail(email: String){
        _emailState.value = email
        Log.d("AuthViewModel", "Email updated to: $email")
    }
}

@Composable
fun SignupView(viewModel: AuthViewModel, navController: NavController) {
    val isSignup = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        SignupCard(
            navController = navController,
            viewModel = viewModel
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupCard( navController: NavController, viewModel: AuthViewModel) {
    val nameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val authError by viewModel.authError.collectAsState()

    // Show error message if any
    authError?.let {
        Toast.makeText(LocalContext.current, it, Toast.LENGTH_LONG).show()
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
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

        ) {
            Card(
                modifier = Modifier.padding(24.dp),
                elevation = CardDefaults.cardElevation(16.dp),
                colors = CardDefaults.cardColors(containerColor = orange)
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = nameState.value,
                        onValueChange = { nameState.value = it },
                        colors = TextFieldDefaults.textFieldColors(containerColor = orange),
                        label = { Text(text = "Name") },
                        placeholder = { Text(text = "Enter your name") }
                    )
                    TextField(
                        value = emailState.value,
                        onValueChange = { emailState.value = it },
                        colors = TextFieldDefaults.textFieldColors(containerColor = orange),
                        label = { Text(text = "Email") },
                        placeholder = { Text(text = "Enter your email") }
                    )
                    TextField(
                        value = passwordState.value,
                        onValueChange = { passwordState.value = it },
                        colors = TextFieldDefaults.textFieldColors(containerColor = orange),
                        label = { Text(text = "Password") },
                        placeholder = { Text(text = "Enter your password") }
                    )

                }

            }
            Button(
                onClick = {
                    viewModel.registerUser(
                        nameState.value,
                        emailState.value,
                        passwordState.value
                    )
                    //save email in vievmodel
                    viewModel.saveUserEmail(emailState.value)
                    navController.navigate(Screen.HomeScreen.route)
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = orange),
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp)
            ) {
                Text(text = "Sign up",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}



