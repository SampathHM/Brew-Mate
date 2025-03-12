package com.example.brewmate.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brewmate.model.Cocktail
import com.example.brewmate.model.CocktailsApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface CocktailUiState {
    data class Success(val cocktails: List<Cocktail>) : CocktailUiState
    object Loading : CocktailUiState
    object Error : CocktailUiState
}

class CocktailViewModel : ViewModel() {
    var cocktailUiState: CocktailUiState by mutableStateOf(CocktailUiState.Success(emptyList()))
        private set

    fun searchCocktails(query: String) {
        viewModelScope.launch {
            cocktailUiState = CocktailUiState.Loading
            try {
                val response = CocktailsApi.getInstance().getCocktails(query)
                cocktailUiState = CocktailUiState.Success(response.drinks ?: emptyList())
            } catch (e: Exception) {
                Log.e("ERROR", "Search failed", e)
                cocktailUiState = CocktailUiState.Error
            }
        }
    }
}