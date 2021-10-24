package com.arasaka.oishii.presentation.mealscategories

import androidx.lifecycle.ViewModel
import com.arasaka.oishii.domain.model.Category
import com.arasaka.oishii.domain.model.Meal
import com.arasaka.oishii.domain.usecase.GetCategories
import com.arasaka.oishii.domain.usecase.GetMealsByCategories
import com.arasaka.oishii.domain.usecase.SaveCategories
import com.arasaka.oishii.domain.usecase.SaveMeals
import com.arasaka.oishii.presentation.BaseViewModel
import com.arasaka.oishii.presentation.meals.MealViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject

@DelicateCoroutinesApi
@HiltViewModel
class CategoriesViewModel@Inject constructor(
    private val getCategories: GetCategories,
    private val saveCategories: SaveCategories,
) : BaseViewModel() {




    fun doGetCategories(categories: String) {
        getCategories(categories) {
            it.fold(::handleFailure) {
                state.value = CategoriesViewState.CategoriesReceived(it.categories ?: listOf())
                saveCategories(it.categories ?: listOf())
                true
            }
        }
    }

    private fun saveCategories(categories:List<Category>){
        saveCategories(categories){
            it.fold(::handleFailure){

            }
        }
    }
}