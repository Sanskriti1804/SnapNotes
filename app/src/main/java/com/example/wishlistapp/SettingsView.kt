package com.example.wishlistapp

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter

@Composable
fun SettingsView(
    wishViewModel: WishViewModel,   // WishViewModel for wishlist-related logic
    //authViewModel: AuthViewModel,   // AuthViewModel for user authentication
    navController: NavController,
    //selectedImageUri : String?
) {
    //username : String?
    //val emailState by authViewModel.emailState.collectAsState() // MAKE SURE `emailState` IS AVAILABLE IN `AuthViewModel`, IF NOT, ADD IT
    //Log.d("SettingsView", "Collected emailState: $emailState")

    //val userName = username ?: "username"

    // To manage the visibility of the profile settings

    val showProfileDialog = remember { mutableStateOf(false) }
    val showLogoutDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as? AppCompatActivity

    val promptManager =
        //BiometricPromptManager(activity = AppCompatActivity())
        activity?.let {
            BiometricPromptManager(it)
        }


    val biometricResult = promptManager?.promptResults?.collectAsState(initial = null)

    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            println("Activity Launcher")
        })

    LaunchedEffect(biometricResult) {
        if (biometricResult?.value is BiometricResult.AuthenticationNotSet) {
            if (Build.VERSION.SDK_INT >= 30) {
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                    )
                }
                enrollLauncher.launch(enrollIntent)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            /*val uri = selectedImageUri?.let {
                Uri.parse(it)
            }
            uri?.let {
                Image(
                    painter = rememberImagePainter(data = it),
                    contentDescription = "profile icon", // UPDATE THE DESCRIPTION TO SOMETHING RELEVANT
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            }*/
            //if (username != null) {
            //Text(text = username, fontWeight = FontWeight.Bold, fontSize = 20.sp)

            Image(
                painter = painterResource(id = R.drawable.icon2),
                contentDescription = "profile icon", // UPDATE THE DESCRIPTION TO SOMETHING RELEVANT
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "username",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                modifier = Modifier.padding(top = 12.dp, bottom = 3.dp)
            )
            //}

            //Log.d("SettingsView", "Display emailState: $emailState")

            /* Display the email from the authViewModel
            if (emailState?.isNotBlank() == true) {
                Text(
                    text = "$emailState",  // ENSURE THAT `emailState` IS COLLECTED CORRECTLY FROM `AuthViewModel`
                    color = Color.DarkGray,
                    fontSize = 16.sp
                )
            } else {*/
            Text(
                text = "Email not available",  // UPDATE THIS MESSAGE IF IT DOESN'T FIT YOUR APP CONTEXT
                color = Color.DarkGray,
                fontSize = 17.sp
            )


            Button(
                onClick =
                { showProfileDialog.value = true },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier.padding(top = 16.dp, bottom = 48.dp)
            ) {
                Text(
                    text = "Edit Profile",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            Text(
                text = "Quick Access",
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Start),
                //textAlign = TextAlign.Left,
                color = Color.DarkGray
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 2.dp),
                backgroundColor = Color.LightGray,
                border = BorderStroke(2.dp, Color.LightGray),
                elevation = 16.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_tag_search),
                            contentDescription = "search icon",
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "Tag Search",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                    Divider(thickness = 2.dp)
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_liked_notes),
                            contentDescription = "liked icon",
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "Liked Notes",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(12.dp)
                                .clickable {
                                    navController.navigate(Screen.ScreenFav.route)
                                }
                        )
                    }
                }
            }

            Text(
                text = "Preferences", fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = 18.dp)
                    .align(Alignment.Start),
                //textAlign = TextAlign.Left,
                color = Color.DarkGray
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 2.dp),
                backgroundColor = Color.LightGray,
                border = BorderStroke(2.dp, Color.DarkGray),
                elevation = 16.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_views),
                            contentDescription = "Views icon",
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(text = "View", fontSize = 18.sp, modifier = Modifier.padding(12.dp))
                    }
                    Divider(thickness = 2.dp)
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_lock),
                            contentDescription = "Lock icon",
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "Lock",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(12.dp)
                                .clickable {
                                    promptManager?.showBiometricPrompt(
                                        title = "Notes Prompt",
                                        description = "Locking notes app for privacy"
                                    )

                                }
                            /*biometricResult?.let { result ->
                                    when (result) {
                                            is BiometricResult.AuthenticationError -> {
                                                result.Error
                                            }

                                            BiometricResult.AuthenticationFailed -> {
                                                "Authentication Failed"
                                            }

                                            BiometricResult.AuthenticationNotSet -> {
                                                "Authentication not set"
                                            }

                                            BiometricResult.AuthenticationSuccess -> {
                                                "Authentication Success"
                                            }

                                            BiometricResult.FeatureUnavailable -> {
                                                "Feature Unavailable"
                                            }

                                            BiometricResult.HardwareUnavailable -> {
                                                "Hardware Unavailable "
                                            }

                                        }
                                }*/

                        )
                    }
                    Divider(thickness = 2.dp)
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_logout),
                            contentDescription = "Log out icon",
                            modifier = Modifier.padding(8.dp),
                            tint = Color.Red
                        )
                        Text(
                            text = "Log Out",
                            fontSize = 18.sp,
                            color = Color.Red,
                            modifier = Modifier
                                .padding(12.dp)
                                .clickable {
                                    showLogoutDialog.value = true
                                }
                        )
                    }
                }
            }
        }
    }

    // Display Profile dialog when `showProfileDialog` is true
    if (showProfileDialog.value) {
        ProfileView(
            onDismiss = { showProfileDialog.value = false },
            navController = navController,
            viewModel = wishViewModel
        )
    }

    // Display Logout dialog when `showLogoutDialog` is true
    if (showLogoutDialog.value) {
        logoutDialog(
            onDismiss = { showLogoutDialog.value = false },
            navController = navController
        )
    }
}


@Composable
fun ProfileView(onDismiss: () -> Unit, navController: NavController, viewModel: WishViewModel) {
    // State to manage the selected image URI
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }
    val userNameState = remember { mutableStateOf("") }

    // Context for permission requests and handling intents
    val context = LocalContext.current

    // Gallery launcher to pick an image
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Get the image Uri from the result data
            val uri = result.data?.data
            selectedImageUri.value = uri
        }
    }


    // Function to open the gallery
    fun openGallery(context: Context, galleryLauncher: ActivityResultLauncher<Intent>) {
        val pickIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        galleryLauncher.launch(pickIntent)
    }

    // Permission launcher for gallery access
    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                // Open the gallery
                openGallery(context, galleryLauncher)
            } else {
                Toast.makeText(
                    context,
                    "Permission denied for gallery access",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(8.dp),
                color = Color.White,
                elevation = 15.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Hello, Name!")

                    // Display the selected image or a placeholder image
                    if (selectedImageUri.value != null) {
                        Image(
                            painter = rememberImagePainter(
                                data = selectedImageUri.value
                            ),
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(100.dp)
                                .clickable {
                                    // Request permission to access the gallery
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                                    } else {
                                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                    }
                                }
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.icon2),
                            contentDescription = "hellokitty",
                            modifier = Modifier
                                .size(100.dp)
                                .clickable {
                                    // Request permission to access the gallery
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                                    } else {
                                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                    }
                                }
                        )
                    }

                    // Username input field
                    TextField(
                        value = userNameState.value,
                        onValueChange = { userNameState.value = it }
                    )

                    // Confirmation button
                    Button(
                        onClick = {
                            //navController.navigate(Screen.SettingsScreen.route + "/${userNameState.value}")
                            //Log.d("Navigation", "Navigating to: ${Screen.SettingsScreen.route}/${userNameState.value}")
                            /*val uri = selectedImageUri.value
                            uri.let {
                                val encodedUri = Uri.encode(it.toString())
                                navController.navigate("${Screen.SettingsScreen.route}/$encodedUri")
                            }*/
                            navController.navigate(Screen.SettingsScreen.route)
                        }, colors = ButtonDefaults.buttonColors(Color.Black),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "OK",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }

@Composable
fun logoutDialog(onDismiss: () -> Unit, navController: NavController) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            elevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Confirm Logout", fontWeight = FontWeight.Bold)
                Text(text = "Are you sure you want to log out?")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(Color.Black),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(text = "Cancel", color = Color.White)
                    }
                    Button(
                        onClick = {
                            navController.navigate(Screen.UserScreen.route) },
                        colors = ButtonDefaults.buttonColors(Color.Black),
                        )
                    {
                        Text(text = "Log Out", color = Color.White)
                    }
                }
            }
        }
    }
}



