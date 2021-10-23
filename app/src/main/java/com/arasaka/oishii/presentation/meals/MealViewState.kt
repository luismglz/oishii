package com.arasaka.oishii.presentation.meals

import com.arasaka.oishii.domain.model.Meal
import com.arasaka.oishii.presentation.BaseViewState

sealed class MealViewState: BaseViewState(){

    data class MealsReceived(val meals: List <Meal>):BaseViewState()
}