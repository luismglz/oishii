package com.arasaka.oishii.domain.usecase

import com.arasaka.oishii.core.interactor.UseCase
import com.arasaka.oishii.domain.model.Category
import com.arasaka.oishii.domain.repository.CategoryRepository
import com.arasaka.oishii.domain.repository.MealRepository
import javax.inject.Inject

class SaveCategories @Inject constructor(private val categoryRepository: CategoryRepository) :
    UseCase<Boolean, List<Category>>() {
    override suspend fun run(params: List<Category>) = categoryRepository.saveCategories(params)

}