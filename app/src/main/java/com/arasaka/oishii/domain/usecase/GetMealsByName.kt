package com.arasaka.oishii.domain.usecase

import com.arasaka.oishii.core.interactor.UseCase
import com.arasaka.oishii.data.dto.MealsResponse
import com.arasaka.oishii.domain.repository.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Inject



class GetMealsByName @Inject constructor(private val mealRepository: MealRepository) :
    UseCase<MealsResponse, String>()  {

    override suspend fun run(params: String ) = mealRepository.getMealsByName(params);
}