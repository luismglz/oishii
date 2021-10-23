package com.arasaka.oishii.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arasaka.oishii.data.dao.MealDao
import com.arasaka.oishii.domain.model.Meal
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Database(entities = [Meal::class], version = 1, exportSchema = false)
abstract class MealDb:RoomDatabase() {


    abstract fun mealDao(): MealDao
}