package com.arasaka.oishii.domain.repository

import com.arasaka.oishii.core.exception.Failure
import com.arasaka.oishii.core.functional.Either
import com.arasaka.oishii.data.dto.MealsResponse
import com.arasaka.oishii.domain.model.Meal

interface MealRepository {
    fun getMealsByName(name:String): Either<Failure, MealsResponse>

    fun getMealsByCategories(category:String):Either<Failure, MealsResponse>

    fun getMealsRandom(name:String):Either<Failure, MealsResponse>

    fun saveMeals(cocktails:List<Meal>): Either<Failure, Boolean>

    fun updateMeal(cocktail:Meal):Either<Failure, Boolean>
}