package com.arasaka.oishii.data.api

import com.arasaka.oishii.data.dto.CategoriesResponse
import com.arasaka.oishii.data.dto.MealsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("json/v1/1/filter.php")
    fun getMealsByName(@Query("s") name: String): Call<MealsResponse>

    @GET("json/v1/1/filter.php")
    fun getMealsByCategories(@Query("c") category: String): Call<MealsResponse>

    @GET("json/v1/1/random.php")
    fun getMealsRandom(@Query("") name: String): Call<MealsResponse>

    @GET("json/v1/1/categories.php")
    fun getCategories(@Query("") name: String): Call<CategoriesResponse>

}