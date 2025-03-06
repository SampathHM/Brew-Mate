// DetailScreen.kt
package com.example.brewmate.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.brewmate.model.Cocktail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(cocktail: Cocktail, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(cocktail.strDrink) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Text("Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            AsyncImage(
                model = cocktail.strDrinkThumb,
                contentDescription = "Cocktail Image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(
                text = cocktail.strInstructions,
                modifier = Modifier.padding(top = 16.dp))
        }
    }
}