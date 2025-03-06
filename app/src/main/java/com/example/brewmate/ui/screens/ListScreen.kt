// ListScreen.kt
package com.example.brewmate.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.brewmate.model.Cocktail

@Composable
fun ListScreen(
    cocktails: List<Cocktail>,
    onItemClick: (Cocktail) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(cocktails) { cocktail ->
            Column(
                Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(cocktail) }
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = cocktail.strDrinkThumb,
                    contentDescription = "Cocktail Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Text(cocktail.strDrink, Modifier.padding(top = 8.dp))
                HorizontalDivider(color = Color.Gray, thickness = 2.dp)
            }
        }
    }
}