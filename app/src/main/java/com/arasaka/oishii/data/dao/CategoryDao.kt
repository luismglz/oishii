package com.arasaka.oishii.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arasaka.oishii.domain.model.Category


@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category")
    fun getCategories(): List<Category>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveCategories(categories: List<Category>):List<Long>

}