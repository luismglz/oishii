package com.arasaka.oishii.data.source

import com.arasaka.oishii.core.exception.Failure
import com.arasaka.oishii.core.functional.Either
import com.arasaka.oishii.core.platform.NetworkHandler
import com.arasaka.oishii.data.api.MealApi
import com.arasaka.oishii.data.dao.CategoryDao
import com.arasaka.oishii.data.dao.MealDao
import com.arasaka.oishii.data.dto.CategoriesResponse
import com.arasaka.oishii.data.dto.MealsResponse
import com.arasaka.oishii.domain.model.Category
import com.arasaka.oishii.domain.model.Meal
import com.arasaka.oishii.domain.repository.CategoryRepository
import com.arasaka.oishii.domain.repository.MealRepository
import com.arasaka.oishii.framework.api.ApiRequest
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val mealApi: MealApi,
    private val networkHandler: NetworkHandler,
    private val mealDao: MealDao
) :
    MealRepository, ApiRequest {

    override fun getMealsByName(name: String): Either<Failure, MealsResponse> {
        val result = makeRequest(
            networkHandler,
            mealApi.getMealsByName(name),
            { it },
            MealsResponse(emptyList())
        ) //Api result

        //If there is an error do this
        return if (result.isLeft) {
            val localResult = mealDao.getMealsByName("%$name%")

            if (localResult.isEmpty()) result //return result of API request
            else Either.Right(MealsResponse(localResult)) //Return the local result
        } else result
    }

    override fun getMealsByCategories(category: String): Either<Failure, MealsResponse> {
        val result = makeRequest(
            networkHandler,
            mealApi.getMealsByCategories(category),
            { it },
            MealsResponse(emptyList())
        ) //Api result

        //If there is an error do this
        return if (result.isLeft) {
            val localResult = mealDao.getMealsByCategories("%$category%")

            if (localResult.isEmpty()) result //return result of API request
            else Either.Right(MealsResponse(localResult)) //Return the local result
        } else result
    }

    override fun getMealsRandom(name: String): Either<Failure, MealsResponse> {
        val result = makeRequest(
            networkHandler,
            mealApi.getMealsRandom(name),
            { it },
            MealsResponse(emptyList())
        ) //Api result

        //If there is an error do this
        return if (result.isLeft) {
            val localResult = mealDao.getMealsRandom()

            if (localResult.isEmpty()) result //return result of API request
            else Either.Right(MealsResponse(localResult)) //Return the local result
        } else result
    }


    override fun saveMeals(meals: List<Meal>): Either<Failure, Boolean> {
        val result = mealDao.saveMeals(meals)
        return if (result.size == meals.size) Either.Right(true)
        else Either.Left(Failure.DatabaseError)
    }


    override fun updateMeal(meal: Meal): Either<Failure, Boolean> {
        TODO("Not yet implemented")
    }


}