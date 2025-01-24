package com.example.littlelemon2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.littlelemon2.navigation.Profile
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon2.R
import com.example.littlelemon2.ui.theme.Highlight1
import com.example.littlelemon2.ui.theme.Highlight2
import com.example.littlelemon2.ui.theme.Karla
import com.example.littlelemon2.ui.theme.Markazi
import com.example.littlelemon2.ui.theme.Primary1
import com.example.littlelemon2.ui.theme.Primary2
import com.example.littlelemon2.utiltiy.AppDatabase
import com.example.littlelemon2.utiltiy.MenuItemRoom

@Composable
fun Home(navController: NavHostController, database: AppDatabase) {

    var starters by remember { mutableStateOf(false) }
    var mains by remember { mutableStateOf(false) }
    var desserts by remember { mutableStateOf(false) }
    var drinks by remember { mutableStateOf(false) }
    var searchPhrase by remember { mutableStateOf("") }


    val databaseMenuItems: List<MenuItemRoom> by database.menuItemDao().getAll().observeAsState(emptyList())

    val menuItems = databaseMenuItems
        .filter { it.title.contains(searchPhrase, ignoreCase = true) }
        .filter {
            if (starters) it.category == "starters" else if (mains) it.category == "mains" else if (desserts) it.category == "desserts" else if (drinks) it.category == "drinks" else true
        }


    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier
                    .size(width = 200.dp, height = 50.dp),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(width = 50.dp, height = 50.dp)
                    .clickable {
                         navController.navigate(Profile.route)
                    }

            )
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(
                containerColor = Primary1
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Little Lemon",
                    color = Primary2,
                    fontSize = 45.sp,
                    fontFamily = Markazi,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                            .weight(1f)
                            .padding(horizontal = 5.dp)
                            .wrapContentSize(Alignment.Center)
                    ) {
                        Text(
                            text = "Chicago",
                            color = Color.White,
                            fontSize = 25.sp,
                            fontFamily = Karla,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                        Text(
                            text = "We are a family owned Mediterranean restaurant, " +
                                    "focused on traditional recipes served with a " +
                                    "modern twist.",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontFamily = Karla,
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.hero_image),
                        contentDescription = "Hero Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.FillBounds,
                    )
                }
                TextField(
                    value = searchPhrase,
                    onValueChange = { searchPhrase = it},
                    singleLine = true,
                    placeholder = {
                        Text(text = "Enter search phrase")
                            },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_search_24),
                            contentDescription = "Search",
                            Modifier.size(23.dp)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Card(
            modifier = Modifier.padding(15.dp),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Text(
                text = "ORDER FOR DELIVERY!",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp,
                fontFamily = Karla,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { starters = !starters },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(starters) Highlight2 else Highlight1,
                        contentColor = if(starters) Highlight1 else Highlight2
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    Text("Starters")
                }
                Button(
                    onClick = { mains = !mains },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(mains) Highlight2 else Highlight1,
                        contentColor = if(mains) Highlight1 else Highlight2
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    Text("Mains")
                }
                Button(
                    onClick = { desserts = !desserts },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(desserts) Highlight2 else Highlight1,
                        contentColor = if(desserts) Highlight1 else Highlight2
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    Text("Desserts")
                }
                Button(
                    onClick = { drinks = !drinks },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(drinks) Highlight2 else Highlight1,
                        contentColor = if(drinks) Highlight1 else Highlight2
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    Text("Drinks")
                }
            }
        }
        MenuItemsList(menuItems)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemsList(items: List<MenuItemRoom>) {
    LazyColumn {
        items(
            items = items,
            itemContent = { menuItem ->
                HorizontalDivider(
                    modifier = Modifier.padding(5.dp),
                    thickness = 1.dp,
                )
                Column (modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                ) {
                    Text(
                        text = menuItem.title,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = Markazi,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentWidth(Alignment.Start)
                                .weight(0.7f)
                                .padding(5.dp)
                        ) {
                            Text(
                                text = menuItem.description,
                                fontFamily = Karla,
                                fontSize = 14.sp,
                                color = Highlight2

                            )

                            Text(
                                text = "$%.2f".format(menuItem.price),
                                fontWeight = FontWeight.Bold,
                                fontFamily = Karla,
                                fontSize = 15.sp,
                                color = Highlight2
                            )

                        }
                        GlideImage(
                            model = menuItem.image,
                            contentDescription = "dish image",
                            modifier = Modifier.size(height = 100.dp, width = 100.dp)
                        )
                    }

                }
            }
        )
    }
}