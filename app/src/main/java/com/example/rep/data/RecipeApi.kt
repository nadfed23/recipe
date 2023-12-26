package com.example.rep.data

import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApi {

    companion object{
        const val BASE_URL = "https://api.api-ninjas.com/v1/recipe?query=bakery"
    }

    @GET("recipes")
    suspend fun getRecipes(): List<Recipe>

    @GET("recipes/{id}")
    suspend fun getRecipe(@Path("id") id: Int): Recipe


}