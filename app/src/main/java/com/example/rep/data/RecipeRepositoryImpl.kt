package com.example.rep.data

import androidx.lifecycle.LiveData
import androidx.room.util.query
import androidx.room.withTransaction
import com.example.rep.Resource
import com.example.rep.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow


class RecipeRepositoryImpl(
    private val api: RecipeApi,
    private val db: RecipeDatabase
) : RecipeRepository {

    private val dao = db.recipeDao()

    override fun getRecipes(): Flow<Resource<List<Recipe>>> = networkBoundResource(
        query = {
            dao.getAllRecipes()
        },
        fetch = {
            delay(1000)
            api.getRecipes()
        },
        saveFetchResult = { recipes ->
            db.withTransaction {
                dao.deleteAllRecipes()
                dao.insertAllRecipes(recipes)
            }
        }
    )
    override fun getRecipe(id: Int): Flow<Resource<Recipe>>  = networkBoundResource(
        query = {
            dao.getRecipe(id)
        },
        fetch = {
            delay(500)
            api.getRecipe(id)
        },
        saveFetchResult = { recipe ->
            db.withTransaction {
                dao.deleteRecipe(recipe)
                dao.insertRecipe(recipe)
            }
        }
    )



    override suspend fun deleteAllRecipes(): Resource<Unit> {
        return try {
            db.withTransaction {
                dao.deleteAllRecipes()
            }
            Resource.Success(Unit)
        }catch (e: Exception){
            Resource.Error(e)
        }
    }
}