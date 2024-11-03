package com.example.wishlistapp

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.wishlistapp.WishData.Wish
import com.example.wishlistapp.ui.theme.Bg1
import com.example.wishlistapp.ui.theme.Bg10
import com.example.wishlistapp.ui.theme.Bg2
import com.example.wishlistapp.ui.theme.Bg3
import com.example.wishlistapp.ui.theme.Bg4
import com.example.wishlistapp.ui.theme.Bg5
import com.example.wishlistapp.ui.theme.Bg6
import com.example.wishlistapp.ui.theme.Bg7
import com.example.wishlistapp.ui.theme.Bg8
import com.example.wishlistapp.ui.theme.Bg9
import kotlin.random.Random

//Main screen of the wishlist app - sets top app bar, FAB and list of wishes that can be swiped to delete

//experimental material api - SwipeToDismiss
@OptIn(ExperimentalMaterialApi::class)
@Composable // it describes how ui elements should be rendered based on the current state and data
//Main screen of app
fun HomeView(
    //for navigation and for state management
    navController: NavController,
    viewModel: WishViewModel
){
    //current context - Toast has a parameter context;
    // so it basically means when the FAB is clicked it triggers the toast and the context
    // in toast is passed as a current context parmeter so that  it can access resources!!??
    val context = LocalContext.current
    //remember state // to manage the state of the components of the scaffold
    val scaffoldState = rememberScaffoldState()
    //Scaffold manages bvarious components like app bar, FAB, drawers, snack msg, etc
    //val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {AppBarView(title= "Hello!",
            navController = navController)},
        /*ModalNavigationDrawer(drawerContent = {
            ModalDrawerSheet {
                navItems.forEachIndexed {
                    index, item ->
                    NavigationDrawerItem(
                        label = {
                                Text(text = navItems.title)
                        },
                        selected = index == selectedItemIndex,
                        onClick = { /*TODO*/ })
                }

            }
        }) {

        },*/
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                contentColor = Color.Black,
                //backgroundColor = Color.Black,
                backgroundColor = Color.White,
                onClick = {
                    //Toast.makeText(context, "FAB Clicked", Toast.LENGTH_LONG).show()
                    navController.navigate(Screen.AddScreen.route + "/0L")

                }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        //getAllWishes - method in vm that return a flow
        //collectAsState - fnn collects values emmited by the flow and returns them as acomposable state that composable can observe and react to
        //initial = listOf() - initial value for the state before any value is emmkited by the flow is set to an empty list

        /* the flow emits the list of wishes so when it is passed to collectasState it coleects those values whichm means the elements of the list and
        returns them as a state of composable so a composable(HomeView) can observe nad react to them , so if i am modifying  awish in the wish list it will be
        reflected in ui and preserved by the state? */
        Box(modifier = Modifier.fillMaxSize()){
            Image(
                painter = painterResource(id = R.drawable.black_bg),
                contentDescription = "Black background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            val wishlist = viewModel.getAllWishes.collectAsState(initial = listOf())
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(it)){
                //items - to iterate over wishlist.value
                //key={wish-> wish.id} - each wish item is identified by its id
                items(wishlist.value, key={wish-> wish.id} ){
                        wish ->
                    //rememberDismissState - creates and remembers the state necessary for handling dismiss actions
                    val dismissState = rememberDismissState(
                        //callback invoked when  the dismiss action(swiping to dismisss) is performed
                        confirmStateChange = {
                            //it - current state of dismissal not the actual item being deleted - checks  how an item is being dismissed
                            //DismissValue - type defined in jet compose lib
                            if(it == DismissValue.DismissedToEnd || it== DismissValue.DismissedToStart){
                                viewModel.deleteWish(wish)
                            }
                            //confirms state change
                            true
                        }
                    )

                    //to wrap each item in the list and enables it to be dismissed by swiping
                    //wraps item -  swipetodismiss is placed around each card or item
                    SwipeToDismiss(
                        state = dismissState,
                        //defines the bg Ui that appears during swipe action
                        background = {
                            //animateColorAsState to animate the bg color based on the swipe direction
                            val color by animateColorAsState(
                                //endtostart(sright to left) is the allowed dismiss direction
                                if(dismissState.dismissDirection
                                    == DismissDirection.EndToStart) Color.Red else Color.Transparent
                                ,label = ""
                            )
                            //alignment of delete icon
                            val alignment = Alignment.CenterEnd
                            //to provide a bg that appears when the user swipes an item to delete
                            Box(
                                //fillmaxsize() -  of the box
                                //color - red as in defined in animateColorAsState
                                Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = 20.dp),
                                //alignment of content(delete icon) within a box
                                contentAlignment = alignment
                            ){
                                Icon(Icons.Default.Delete,
                                    contentDescription = "Delete Icon",
                                    tint = Color.White)
                            }

                        },
                        //swiping right to left triggers the dismissal
                        directions = setOf(DismissDirection.EndToStart),
                        //sets the threshold(how much an item needs to be swiped) for dismissing an item (1f- item dismissed when fully swiped)
                        dismissThresholds = {FractionalThreshold(1f)},
                        //defines the content of each item that can be dismissed -what happens when an item is dismissed
                        dismissContent = {
                            WishItem(wish = wish, onClick = {
                                val id = wish.id
                                navController.navigate(Screen.AddScreen.route + "/$id")
                            }, viewModel = viewModel)
                        }
                    )

                }
            }
        }
    }
}


@Composable
fun WishItem(wish: Wish, onClick: () -> Unit, viewModel: WishViewModel){

    val isFav = remember { mutableStateOf(false) }

    val BgColor = listOf<Color>(Bg1, Bg2, Bg3,Bg4,Bg5,Bg6,Bg7,Bg8,Bg9,Bg10)
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        .clickable {
            onClick()
        },
        elevation = 10.dp,
        //random color for bg
        backgroundColor = BgColor[Random.nextInt(BgColor.size)]
    ) {
        Column(modifier = Modifier.padding(16.dp)){
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description)
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
                //verticalArrangement = Arrangement.SpaceBetween
            )
            {
                Icon(
                    imageVector = if (isFav.value){
                        Icons.Outlined.Favorite
                    }
                    else{
                        Icons.Filled.Favorite
                    },
                    contentDescription = "Fav Note",
                    modifier = Modifier
                        .clickable { isFav.value = !isFav.value },
                    tint = if (isFav.value) Color.Red else Color.LightGray
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(onClick = { },
                    //colors = ButtonDefaults.buttonColors(#)
                ) {
                    Text(text = "Tag")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScreenFav(navController: NavController, viewModel: WishViewModel){
    
    val scaffoldState = rememberScaffoldState()
    
    val favWishes = viewModel.favWishes.collectAsState(initial = listOf())

    Scaffold(
            scaffoldState = scaffoldState,
            topBar = {AppBarView(title= "Favorite Notes",
                navController = navController)}
        ){
            Box(modifier = Modifier.fillMaxSize()){
                Image(
                    painter = painterResource(id = R.drawable.black_bg),
                    contentDescription = "Black background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(it)){
                    //items - to iterate over wishlist.value
                    //key={wish-> wish.id} - each wish item is identified by its id
                    items(favWishes.value){
                            wish ->
                        //rememberDismissState - creates and remembers the state necessary for handling dismiss actions
                        val dismissState = rememberDismissState(
                            //callback invoked when  the dismiss action(swiping to dismisss) is performed
                            confirmStateChange = {
                                //it - current state of dismissal not the actual item being deleted - checks  how an item is being dismissed
                                //DismissValue - type defined in jet compose lib
                                if(it == DismissValue.DismissedToEnd || it== DismissValue.DismissedToStart){
                                    viewModel.deleteWish(wish)
                                }
                                //confirms state change
                                true
                            }
                        )

                        //to wrap each item in the list and enables it to be dismissed by swiping
                        //wraps item -  swipetodismiss is placed around each card or item
                        SwipeToDismiss(
                            state = dismissState,
                            //defines the bg Ui that appears during swipe action
                            background = {
                                //animateColorAsState to animate the bg color based on the swipe direction
                                val color by animateColorAsState(
                                    //endtostart(sright to left) is the allowed dismiss direction
                                    if(dismissState.dismissDirection
                                        == DismissDirection.EndToStart) Color.Red else Color.Transparent
                                    ,label = ""
                                )
                                //alignment of delete icon
                                val alignment = Alignment.CenterEnd
                                //to provide a bg that appears when the user swipes an item to delete
                                Box(
                                    //fillmaxsize() -  of the box
                                    //color - red as in defined in animateColorAsState
                                    Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(horizontal = 20.dp),
                                    //alignment of content(delete icon) within a box
                                    contentAlignment = alignment
                                ){
                                    Icon(Icons.Default.Delete,
                                        contentDescription = "Delete Icon",
                                        tint = Color.White)
                                }

                            },
                            //swiping right to left triggers the dismissal
                            directions = setOf(DismissDirection.EndToStart),
                            //sets the threshold(how much an item needs to be swiped) for dismissing an item (1f- item dismissed when fully swiped)
                            dismissThresholds = {FractionalThreshold(1f)},
                            //defines the content of each item that can be dismissed -what happens when an item is dismissed
                            dismissContent = {
                                WishItem(wish = wish, onClick = {
                                    val id = wish.id
                                    navController.navigate(Screen.AddScreen.route + "/$id")
                                }, viewModel = viewModel)
                            }
                        )

                    }
                }
            }

    }
}


