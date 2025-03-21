package com.example.brewmate.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brewmate.model.Cocktail
import com.example.brewmate.model.CocktailsApi
import kotlinx.coroutines.launch

sealed interface CocktailUiState {
    data class Success(val cocktails: List<Cocktail>) : CocktailUiState
    data object Loading : CocktailUiState
    data object Error : CocktailUiState
    data object Empty : CocktailUiState
}

class CocktailViewModel : ViewModel() {
    var cocktailUiState: CocktailUiState by mutableStateOf(CocktailUiState.Success(emptyList()))
        private set

    fun searchCocktails(query: String) {
        viewModelScope.launch {
            cocktailUiState = CocktailUiState.Loading
            try {
                val response = CocktailsApi.getInstance().getCocktails(query)
                cocktailUiState = if (response.drinks.isNullOrEmpty()) {
                    CocktailUiState.Empty
                } else {
                    CocktailUiState.Success(response.drinks)
                }
            } catch (e: Exception) {
                Log.e("ERROR", "Search failed", e)
                cocktailUiState = CocktailUiState.Error
            }
        }
    }
}