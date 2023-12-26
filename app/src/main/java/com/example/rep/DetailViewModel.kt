package com.example.rep

import androidx.lifecycle.*
import com.example.rep.data.Recipe
import com.example.rep.data.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _recipe = _id.switchMap { id ->
        repository.getRecipe(id).asLiveData()
    }

    val recipe: LiveData<Resource<Recipe>> = _recipe


    fun start(id: Int) {
        _id.value = id
    }
}