package com.example.brewmate.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class Cocktail(
    val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String,
    val strInstructions: String
)

const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

interface CocktailsApi {
    @GET("search.php")
    suspend fun getCocktails(
        @Query("s") query: String
    ): CocktailResponse

    companion object{
        var cocktailsService: CocktailsApi? = null

        fun getInstance(): CocktailsApi {
            if (cocktailsService === null) {
                cocktailsService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(CocktailsApi::class.java)
            }
            return cocktailsService!!
        }
    }
}

data class CocktailResponse(
    val drinks: List<Cocktail>
)