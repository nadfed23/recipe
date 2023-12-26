package com.example.rep.data


import com.example.rep.Resource
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    fun getRecipes(): Flow<Resource<List<Recipe>>>

    fun getRecipe(id: Int): Flow<Resource<Recipe>>

    suspend fun deleteAllRecipes(): Resource<Unit>
}