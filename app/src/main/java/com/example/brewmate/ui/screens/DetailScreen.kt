// DetailScreen.kt
package com.example.brewmate.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.brewmate.R
import com.example.brewmate.model.Cocktail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(cocktail: Cocktail, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(cocktail.strDrink, style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button), // "Back"
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Cocktail Image
            AsyncImage(
                model = cocktail.strDrinkThumb,
                contentDescription = stringResource(R.string.content_description_cocktail_image),// "Cocktail Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Cocktail Name
            Text(
                text = cocktail.strDrink,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Cocktail Instructions
            Text(
                text = cocktail.strInstructions ?: stringResource(R.string.error_no_instructions), // "No instructions available"
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Ingredients Section
            Text(
                text = stringResource(R.string.detail_ingredients),// "Ingredients",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Dynamically Display Ingredients and Measurements
            val ingredients = listOf(
                cocktail.strIngredient1 to cocktail.strMeasure1,
                cocktail.strIngredient2 to cocktail.strMeasure2,
                cocktail.strIngredient3 to cocktail.strMeasure3,
                cocktail.strIngredient4 to cocktail.strMeasure4,
                cocktail.strIngredient5 to cocktail.strMeasure5,
                cocktail.strIngredient6 to cocktail.strMeasure6,
                cocktail.strIngredient7 to cocktail.strMeasure7,
                cocktail.strIngredient8 to cocktail.strMeasure8,
                cocktail.strIngredient9 to cocktail.strMeasure9,
                cocktail.strIngredient10 to cocktail.strMeasure10,
                cocktail.strIngredient11 to cocktail.strMeasure11,
                cocktail.strIngredient12 to cocktail.strMeasure12,
                cocktail.strIngredient13 to cocktail.strMeasure13,
                cocktail.strIngredient14 to cocktail.strMeasure14,
                cocktail.strIngredient15 to cocktail.strMeasure15
            )

            ingredients.forEach { (ingredient, measure) ->
                if (!ingredient.isNullOrEmpty()) {
                    Text(
                        text = "• $ingredient: ${measure ?: stringResource(R.string.measure_default)}",//"to taste"
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                    )
                }
            }
        }
    }
}