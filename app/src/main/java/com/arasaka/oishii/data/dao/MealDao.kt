package com.arasaka.oishii.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.arasaka.oishii.domain.model.Meal

@Dao
interface MealDao {
    @Query("SELECT * FROM Meal WHERE name LIKE :filter")
    fun getMealsByName(filter:String): List<Meal>

    @Query("SELECT * FROM Meal WHERE category LIKE :filter")
    fun getMealsByCategories(filter:String): List<Meal>

    @Query("SELECT * FROM Meal")
    fun getMealsRandom(): List<Meal>

    @Insert(onConflict = IGNORE)
    fun saveMeals(meals: List<Meal>):List<Long>//return Long because is the ID of meal which is brought from api

    @Update
    fun updateMeals(meal: Meal): Int
}