package com.arasaka.oishii.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arasaka.oishii.data.dao.CategoryDao
import com.arasaka.oishii.domain.model.Category

@Database(entities = [Category::class], version = 1, exportSchema = false)
abstract class CategoryDb : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}