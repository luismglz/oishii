package com.arasaka.oishii.domain.usecase

import com.arasaka.oishii.core.interactor.UseCase
import com.arasaka.oishii.data.dto.CategoriesResponse
import com.arasaka.oishii.data.dto.MealsResponse
import com.arasaka.oishii.domain.repository.CategoryRepository
import com.arasaka.oishii.domain.repository.MealRepository
import javax.inject.Inject

class GetCategories @Inject constructor(private val categoryRepository: CategoryRepository) :
    UseCase<CategoriesResponse, String>() {

    override suspend fun run(params: String) = categoryRepository.getCategories(params);
}