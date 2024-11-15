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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
    navController: NavController,
    //authViewModel: AuthViewModel,   // AuthViewModel for user authentication
    //selectedImageUri : String?,
//    username : String
) {

    val username by wishViewModel.username.collectAsState()
    val photoUri by wishViewModel.photoUri.collectAsState()

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

            photoUri?.let {
                Image(
                    painter = rememberImagePainter(data = it),
                    contentDescription = "profile icon", // UPDATE THE DESCRIPTION TO SOMETHING RELEVANT
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            } ?: run {
                //if (username != null) {
                //Text(text = username, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Image(
                    painter = painterResource(id = R.drawable.icon2),
                    contentDescription = "profile icon", // UPDATE THE DESCRIPTION TO SOMETHING RELEVANT
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            }
            Text(
                text = username.ifEmpty { "Username" },
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                modifier = Modifier.padding(top = 12.dp, bottom = 3.dp)
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
                text = "Preference",
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
                            painter = painterResource(id = R.drawable.ic_lock),
                            contentDescription = "lock icon",
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "App Lock",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(12.dp)
                                .clickable {
                                    promptManager?.showBiometricPrompt(
                                        title = "Notes Prompt",
                                        description = "Locking notes app for privacy"
                                    )
                                }
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
//                                    navController.navigate(Screen.ScreenFav.route)
                                }
                        )
                    }
                }
            }

            Text(
                text = "About us & Support", fontSize = 20.sp,
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
                border = BorderStroke(2.dp, Color.LightGray),
                elevation = 16.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_info),
                            contentDescription = "Info icon",
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "App Info",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(12.dp)
                                .clickable { navController.navigate(Screen.InfoPrevScreen.route) }
                        )
                    }
                    Divider(thickness = 2.dp)
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_contact),
                            contentDescription = "Contact us icon",
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "Contact us",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(12.dp)
                                .clickable {
                                    //creating an intent to open email app with few prefilled deatils
                                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                        //ensures only email apps respond
                                        data = Uri.parse("mailto:")
                                        putExtra(Intent.EXTRA_EMAIL, arrayOf("support@snapnotes.com"))
                                    }
                                    //start the email intent
                                    context.startActivity(emailIntent)
                                }
                        )
                    }
                    Divider(thickness = 2.dp)
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_feedback),
                            contentDescription = "feedback icon",
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "Feedback and Suggestion",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(12.dp)
                                .clickable {
                                    //creating an intent to open email app with few prefilled deatils
                                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                        //ensures only email apps respond
                                        data = Uri.parse("mailto:")
                                        putExtra(Intent.EXTRA_EMAIL, arrayOf("support@snapnotes.com"))
                                        putExtra(Intent.EXTRA_SUBJECT, "Suggestion and Feedbacks")
                                    }
                                    //start the email intent
                                    context.startActivity(emailIntent)
                                }
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
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var userNameState by remember { mutableStateOf("") }

    // Context for permission requests and handling intents
    val context = LocalContext.current
    
    // Gallery launcher to pick an image
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Get the image Uri from the result data
            val uri = result.data?.data
            selectedImageUri = uri
            uri?.let {
                // Update the photo URI in the ViewModel
                viewModel.updatePhotoUri(it)
            }
        }
    }

    /*
    gallery launcher -
    rememberlauncher - to remember the result and use it to start another activity
    contract - telling the app that the result will be used for something else
    handling activity result if it is successful
    uri = result.data?.data - returns the sata returned from the gallery (uri of the selected image)
    result.data - returned intent obj
    result.data?.data - access the specific data within the intent(uri)
     */

    // Function to open the gallery
    fun openGallery(context: Context, galleryLauncher: ActivityResultLauncher<Intent>) {
        val pickIntent = Intent(
            Intent.ACTION_PICK,     //picks some data
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI        //points to the locn where img are stored on the device
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
                    Text(text = "Hello!!")

                    // Display the selected image or a placeholder image
                    if (selectedImageUri != null) {
                        Image(
                            painter = rememberImagePainter(
                                data = selectedImageUri
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
                        value = userNameState,
                        onValueChange = { userNameState = it }
                    )

                    // Confirmation button
                    Button(
                        onClick = {
                            viewModel.updateUsername(userNameState)
                            onDismiss() // Close the ProfileView dialog
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



