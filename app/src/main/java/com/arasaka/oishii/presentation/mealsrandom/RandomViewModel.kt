package com.arasaka.oishii.presentation.mealsrandom

import androidx.lifecycle.ViewModel
import com.arasaka.oishii.domain.model.Meal
import com.arasaka.oishii.domain.usecase.GetMealsRandom
import com.arasaka.oishii.domain.usecase.SaveMeals
import com.arasaka.oishii.presentation.BaseViewModel
import com.arasaka.oishii.presentation.meals.MealViewState
import javax.inject.Inject

class RandomViewModel@Inject constructor(
    private val getMealsRandom: GetMealsRandom,
    private val saveMeals: SaveMeals
) : BaseViewModel() {

    fun getMealsRandom() {
        getMealsRandom(""){
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