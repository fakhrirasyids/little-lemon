package com.fakhrirasyids.littlelemon.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.fakhrirasyids.littlelemon.R
import com.fakhrirasyids.littlelemon.data.local.AppDatabase
import com.fakhrirasyids.littlelemon.data.local.MenuItemRoom
import com.fakhrirasyids.littlelemon.ui.navigation.ProfileDestination
import com.fakhrirasyids.littlelemon.ui.theme.LittleLemonColor


@Composable
fun HomeScreen(navController: NavController, database: AppDatabase) {
    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(initial = emptyList())

    Column(Modifier.fillMaxSize().background(Color.White)) {
        HomeHeader(navController)
        HeroSection(databaseMenuItems)
    }
}


@Composable
fun HomeHeader(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.height(32.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_acc),
            contentDescription = "Profile",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable {
                    navController.navigate(ProfileDestination.route)
                }
        )
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HeroSection(menuItemsLocal: List<MenuItemRoom>) {
    var searchPhrase by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    val filteredItems = menuItemsLocal.filter {
        (searchPhrase.isBlank() || it.title.contains(searchPhrase, ignoreCase = true)) &&
                (selectedCategory.isBlank() || it.category.equals(selectedCategory, ignoreCase = true))
    }

    Column {
        // Green Hero Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(LittleLemonColor.green)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Little Lemon",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = LittleLemonColor.yellow
                    )
                    Text(
                        text = "Chicago",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.padding(top = 12.dp, end = 8.dp)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.hero_image),
                    contentDescription = "Hero Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }

            // Search bar
            OutlinedTextField(
                value = searchPhrase,
                onValueChange = { searchPhrase = it },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                placeholder = { Text("Enter search phrase") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
            )
        }

        // Order Title
        Text(
            "ORDER FOR DELIVERY!",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        // Categories with toggle
        val categories = listOf("Starters", "Mains", "Desserts", "Drinks")

        Row(
            Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            categories.forEach { category ->
                val categoryLower = category.lowercase()
                val isSelected = selectedCategory == categoryLower

                FilterChip(
                    onClick = {
                        selectedCategory = if (isSelected) "" else categoryLower
                    },
                    selected = isSelected,
                    label = {
                        Text(
                            category,
                            color = if (isSelected) Color.White else Color.Black
                        )
                    },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = LittleLemonColor.green,
                        containerColor = Color(0xFFF3F3F3)
                    )
                )
            }
        }

        // Menu list
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            items(filteredItems) { menuItem ->
                MenuItemCard(menuItem)
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemCard(menuItem: MenuItemRoom) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(menuItem.title, style = MaterialTheme.typography.titleMedium)
            Text(
                menuItem.desc,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(vertical = 4.dp),
                maxLines = 2
            )
            Text(
                "$${menuItem.price}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }
        GlideImage(
            model = menuItem.image,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

