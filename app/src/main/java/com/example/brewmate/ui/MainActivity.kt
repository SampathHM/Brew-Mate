package com.example.brewmate.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.brewmate.ui.screens.DetailScreen
import com.example.brewmate.ui.screens.ListScreen
import com.example.brewmate.ui.theme.BrewMateTheme
import com.example.brewmate.viewmodel.CocktailViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BrewMateTheme {
                    BrewMate()
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BrewMate() {
    val navController = rememberNavController()
    val cocktailViewModel: CocktailViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "search"
    ) {
        composable("search") {
            SearchScreen(navController, cocktailViewModel)
        }
        composable("detail/{drinkId}") { backStackEntry ->
            val drinkId = backStackEntry.arguments?.getString("drinkId")
            val cocktail = cocktailViewModel.cocktails.value
                .firstOrNull { it.idDrink == drinkId }

            if (cocktail != null) {
                DetailScreen(cocktail, navController)
            } else {
                Text("Cocktail not found", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: CocktailViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.primary
                        )
                    )
                )
        ){
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                "BrewMate",
                Modifier.fillMaxWidth().padding(bottom = 16.dp),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Discover and explore cocktail recipes",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search cocktails") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Button(
                onClick = {
                    viewModel.searchCocktails(searchQuery)
                    keyboardController?.hide()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("Search")
            }

            when {
                viewModel.isLoading.collectAsState().value -> {
                    CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                }

                viewModel.cocktails.collectAsState().value.isEmpty() -> {
                    Text("No cocktails found", Modifier.padding(16.dp))
                }

                else -> {
                    ListScreen(
                        cocktails = viewModel.cocktails.collectAsState().value,
                        onItemClick = { navController.navigate("detail/${it.idDrink}") }
                    )
                }
            }
        }
    }
    }
}