package com.arasaka.oishii.data.source

import com.arasaka.oishii.core.exception.Failure
import com.arasaka.oishii.core.functional.Either
import com.arasaka.oishii.core.platform.NetworkHandler
import com.arasaka.oishii.data.api.MealApi
import com.arasaka.oishii.data.dao.CategoryDao
import com.arasaka.oishii.data.dto.CategoriesResponse
import com.arasaka.oishii.domain.model.Category
import com.arasaka.oishii.domain.repository.CategoryRepository
import com.arasaka.oishii.framework.api.ApiRequest
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val mealApi: MealApi,
    private val networkHandler: NetworkHandler,
    private val categoryDao: CategoryDao
) :
    ApiRequest, CategoryRepository {

    override fun getCategories(name: String): Either<Failure, CategoriesResponse> {
        val result = makeRequest(
            networkHandler,
            mealApi.getCategories(name),
            { it },
            CategoriesResponse(emptyList())
        ) //Api result

        return if (result.isLeft) {
            val localResult = categoryDao.getCategories()

            if (localResult.isEmpty()) result
            else Either.Right(CategoriesResponse(localResult)) //Return the local result
        } else result
    }

    override fun saveCategories(categories: List<Category>): Either<Failure, Boolean> {
        val result = categoryDao.saveCategories(categories)
        return if (result.size == categories.size) Either.Right(true)
        else Either.Left(Failure.DatabaseError)
    }
}