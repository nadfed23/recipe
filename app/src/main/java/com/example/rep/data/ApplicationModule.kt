package com.example.rep.data

import android.app.Application
import androidx.room.Room
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideRecipeApi(): RecipeApi = Retrofit.Builder()
        .baseUrl(RecipeApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(RecipeApi::class.java)

    @Provides
    fun provideMainRepository(api: RecipeApi, db: RecipeDatabase): RecipeRepository = RecipeRepositoryImpl(api, db)

    @Singleton
    @Provides
    fun provideDatabase(app: Application){
        Room.databaseBuilder(app, RecipeDatabase::class.java, "recipe_database")
            .addMigrations()
            .build()
    }
}