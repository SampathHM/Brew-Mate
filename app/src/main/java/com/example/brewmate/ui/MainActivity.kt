package com.example.brewmate.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.brewmate.model.Cocktail
import com.example.brewmate.ui.theme.BrewMateTheme
import com.example.brewmate.viewmodel.CocktailViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BrewMateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BrewMate(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun BrewMate(modifier: Modifier = Modifier) {
    val cocktailViewModel: CocktailViewModel = viewModel()
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = modifier.padding(16.dp)) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search cocktails") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )

        // Search Button
        Button(
            onClick = {
                cocktailViewModel.searchCocktails(searchQuery)
                keyboardController?.hide()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Search")
        }

        // Loading/Results
        when {
            cocktailViewModel.isLoading.collectAsState().value -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            cocktailViewModel.cocktails.collectAsState().value.isEmpty() -> {
                Text("No cocktails found", modifier = Modifier.padding(16.dp))
            }
            else -> {
                CocktailList(cocktails = cocktailViewModel.cocktails.collectAsState().value)
            }
        }
    }
}


@Composable
fun CocktailList(cocktails: List<Cocktail>) {
    LazyColumn {
        items(cocktails) { cocktail ->
            Text(
                text = cocktail.idDrink,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
            Text(
                text = cocktail.strDrink,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
            Text(
                text = cocktail.strInstructions,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
            AsyncImage(
                model = cocktail.strDrinkThumb,
                contentDescription = "Cocktail Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            HorizontalDivider(color = Color.Gray, thickness = 2.dp)
        }
    }
}

