package com.arasaka.oishii.presentation.meals

import androidx.lifecycle.ViewModel
import com.arasaka.oishii.domain.model.Meal
import com.arasaka.oishii.domain.usecase.GetMealsByCategories
import com.arasaka.oishii.domain.usecase.GetMealsByName
import com.arasaka.oishii.domain.usecase.GetMealsRandom
import com.arasaka.oishii.domain.usecase.SaveMeals
import com.arasaka.oishii.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject

@DelicateCoroutinesApi
@HiltViewModel
class MealViewModel @Inject constructor(
    private val getMealsByName: GetMealsByName,
    private val saveMeals: SaveMeals
) : BaseViewModel() {

    fun doGetMealsByName(name: String) {
        getMealsByName(name) {
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





    //:: -> convert function to a lambda and it passes the params directly

}