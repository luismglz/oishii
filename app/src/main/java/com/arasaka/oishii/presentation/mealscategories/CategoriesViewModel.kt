package com.arasaka.oishii.presentation.mealscategories

import androidx.lifecycle.ViewModel
import com.arasaka.oishii.domain.model.Meal
import com.arasaka.oishii.domain.usecase.GetMealsByCategories
import com.arasaka.oishii.domain.usecase.SaveMeals
import com.arasaka.oishii.presentation.BaseViewModel
import com.arasaka.oishii.presentation.meals.MealViewState
import javax.inject.Inject

class CategoriesViewModel@Inject constructor(
    private val getMealsByCategories: GetMealsByCategories,
    private val saveMeals: SaveMeals
) : BaseViewModel() {


    fun getMealsByCategories(category: String) {
        getMealsByCategories(category) {
            it.fold(::handleFailure) {
                state.value = MealViewState.MealsReceived(it.meals ?: listOf())
                saveMeals(it.meals ?: listOf())
                true
            }
        }
    }

    private fun saveMeals(meals:List<Meal>){
        saveMeals(meals){
            it.fold(::handleFailure){

            }
        }
    }
}