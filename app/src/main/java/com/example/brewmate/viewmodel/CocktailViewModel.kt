package com.example.brewmate.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brewmate.model.Cocktail
import com.example.brewmate.model.CocktailsApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CocktailViewModel : ViewModel() {
    private val _cocktails = MutableStateFlow<List<Cocktail>>(emptyList())
    val cocktails: StateFlow<List<Cocktail>> = _cocktails

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun searchCocktails(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = CocktailsApi.getInstance().getCocktails(query)
                _cocktails.value = response.drinks ?: emptyList()
            } catch (e: Exception) {
                Log.e("ERROR", "Search failed", e)
                _cocktails.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}