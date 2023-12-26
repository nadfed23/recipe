package com.example.rep

import androidx.lifecycle.*
import com.example.rep.data.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val loadData = MutableLiveData(Unit)

    val recipes = loadData.switchMap {
        repository.getRecipes().asLiveData()
    }

    fun deleteAll() {
        viewModelScope.launch{
            repository.deleteAllRecipes()
        }
    }

    fun refresh() {
        loadData.value = Unit
    }
}