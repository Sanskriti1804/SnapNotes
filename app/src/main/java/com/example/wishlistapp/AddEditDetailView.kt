package com.example.wishlistapp

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wishlistapp.WishData.Wish
import kotlinx.coroutines.launch

//UI for adding and editing a wish
@Composable
fun AddEditDetailView(
    id: Long,       // ID of a wish //ID - 0L a new wish is being added // else an existing wish is being edited
    viewModel: WishViewModel,       // handles the logic
    navController: NavController        // for navigating bw screens
) {

    //mutable state holding the msg to be displayed
    val snackMessage = remember{
        mutableStateOf("")
    }
    //coroutine scope for launching coroutine
    val scope = rememberCoroutineScope()

    //state of scaffold for managing the snackbar and other components
    val scaffoldState = rememberScaffoldState()

    val newTag = remember{
        mutableStateOf("")
    }

    //if id is not 0L the existing wish is fetched and VM is updated w title and desc
    if(id != 0L){
        //getAWishById - g=fetches wish of given id and returns a flow that emits the wish data
        //collectasState - collects the emmited data anfd onverts it to a state obj
        // initial value is set to empty string then it is being updated to the string to display on ui
        val wish = viewModel.getAWishById(id).collectAsState(
            initial = Wish(0L, "", "", emptyList()))
        //updates the vm w existing wish data
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
        viewModel.wishTagState = wish.value.tag
    }
    else{
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
        viewModel.wishTagState = emptyList()
    }

    // to manage the top app bar and the snackbar
    //[scaffold - top level app structure including app bar and snackbar
    //column- vertical layout of ui components
    //spacer - for adding space bw elements]
    Scaffold(
        //lambda fnn
          topBar = {
            AppBarView(// setting title to display of the top app bar
                //!0L - existing  wish //stringResource - to fetch string valuefrom the resource directory during runtime
                //fetchin string resource  with the id - R.string.update_wish  in string.xml
                title = if (id != 0L) stringResource(id = R.string.update_wish)
                else stringResource(id = R.string.add_wish),
                onBackNavClicked = { navController.navigateUp() },
                navController = navController
            )},
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //adds space bw elements
            Spacer(modifier = Modifier.height(10.dp))

            // Adding a wish
            WishTextField(
                label = "Title" ,
                value = viewModel.wishTitleState,
                onValueChanged = {
                    viewModel.onWishTitleChanged(it)
                })
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(
                label = "Note" ,
                value = viewModel.wishDescriptionState,
                onValueChanged = {
                    viewModel.onWishDescriptionState(it)
                })
            Spacer(modifier = Modifier.height(10.dp))
//            WishTextField(
//                label = "Tag" ,
//                value = viewModel.wishTagState.joinToString(","),
//                onValueChanged = {
//                    viewModel.onWishTagState(it)
//                })
            TagInput(
                tags = viewModel.wishTagState,
                onAddTag = { tag ->
                    viewModel.wishTagState = viewModel.wishTagState + tag // Add new tag
                },
                onRemoveTag = { tag ->
                    viewModel.wishTagState = viewModel.wishTagState.filter { it != tag } // Remove tag
                },
                newTag = newTag.value,
                onNewTagChanged = { newTag.value = it } // Update new tag input
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                if(viewModel.wishTitleState.isNotEmpty() &&
                    viewModel.wishDescriptionState.isNotEmpty() && viewModel.wishTagState.isNotEmpty()){
                    if(id != 0L){
                        //update a wish
                        viewModel.updateWish(
                            Wish(
                                //each wish has an unique id
                                id = id,
                                title = viewModel.wishTitleState.trim(),
                                description = viewModel.wishDescriptionState.trim(),
                                tag = viewModel.wishTagState
                            )
                        )

                    }
                    else{
                        //AddWish
                        viewModel.addWish(
                            //wish obj to pass that to addWish
                            Wish(
                                //addWish is taking title from wishTitleState; as the state changes it is uodated in the ui
                                //trim() - removing whitespace from beginning and end
                                title = viewModel.wishTitleState.trim(),
                                description = viewModel.wishDescriptionState.trim(),
                                tag = viewModel.wishTagState
                            )
                        )
                        snackMessage.value = "Wish has been created"
                    }

            }
            else{
                //when user hasnt filled out all required fields
                snackMessage.value = "Enter fields to create a wish"
                }
                scope.launch {
                    //scope - to tie it to the lifecycle of the function
                    scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                    navController.navigateUp()
                }
            },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
                    //.padding(start = 12.dp, end = 12.dp)
            )
            {
                Text(
                    text = if (id != 0L) stringResource(id = R.string.update_wish)
                else stringResource(id = R.string.add_wish),
                    style = TextStyle(fontSize = 18.sp),
                    color = Color.White
                )

            }
        }

    }
}

//creating text i/p fields
@Composable
fun WishTextField(
    label : String,
    value : String, // current value
    onValueChanged : (String) -> Unit   //call back fun invoked when the value of the text field changes
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label, color = Color.Black)},
        modifier = Modifier.fillMaxWidth(),
        //keyboard type - text
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            //using predefined Color
            textColor = Color.Black,
            //using our own colors
            focusedBorderColor = colorResource(id = R.color.black),
            unfocusedBorderColor = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            unfocusedLabelColor = colorResource(id = R.color.black),
        )
    )
}

@Preview(showBackground = true)
@Composable
fun WistPrev() {
    // WishTextField(label = "text", value = "value", onValueChanged = {})
    // Create a dummy ViewModel with some initial state
    val dummyViewModel = WishViewModel().apply {
        wishTitleState = "Sample Title"
        wishDescriptionState = "Sample Description"
    }

    // Use rememberNavController for the preview
    val navController = rememberNavController()

    AddEditDetailView(
        id = 0L, // You can change this to simulate editing an existing wish
        viewModel = dummyViewModel,
        navController = navController
    )
}

@Composable
fun TagInput(
    tags : List<String>,
    onAddTag : (String) -> Unit,
    onRemoveTag : (String) -> Unit,
    newTag : String,
    onNewTagChanged : (String) -> Unit
){
    //displaying existing tags
    Column {
        LazyRow (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
//            horizontalArrangement = Arrangement.Start
        ){
            items(tags) {
                tag ->
                TagItem(
                    tag = tag,
                    onRemoveTag = { onRemoveTag(tag)
                    }
                )
            }
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                value = newTag,
                onValueChange = onNewTagChanged,
                label = { Text(text = "Tag", color = Color.Black)},
                modifier = Modifier.weight(2f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                if (newTag.isNotEmpty()) {
                    onAddTag(newTag)
                    onNewTagChanged("")
                }
            },
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .height(56.dp)
                    .width(56.dp)
                    .padding(top = 4.dp),
                colors = ButtonDefaults.buttonColors(Color.Black)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add tag",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp))
            }
        }
        }
}

@Composable
fun TagItem( tag : String, onRemoveTag: () -> Unit){
    Box (
        modifier = Modifier
            .padding(3.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 5.dp),
//        verticalAlignment = Alignment.CenterVertically
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 4.dp)
        ){
            Text(
                text = "#$tag",
                fontSize = 12.sp,
                modifier = Modifier
//                    .weight(1f)
                    .padding(end = 4.dp)
                    .align(Alignment.CenterVertically)
            )
//            Button(onClick = {
//
//            },
//                modifier = Modifier
//                    ,
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Transparent,
//                    contentColor = Color.Black
//                )) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Remove tag",
                    tint = Color.Black,
                    modifier = Modifier
                        //.size(30.dp)
                        .height(16.dp)
                        .width(16.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            onRemoveTag()
                        }
                )

            }
        }
    }




