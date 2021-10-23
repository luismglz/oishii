package com.arasaka.oishii.domain.usecase

import com.arasaka.oishii.core.interactor.UseCase
import com.arasaka.oishii.domain.model.Meal
import com.arasaka.oishii.domain.repository.MealRepository
import javax.inject.Inject

class SaveMeals @Inject constructor(private val mealRepository: MealRepository):
    UseCase<Boolean, List<Meal>>(){
    override suspend fun run(params: List<Meal>) = mealRepository.saveMeals(params)

}