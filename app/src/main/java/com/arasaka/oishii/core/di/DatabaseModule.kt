package com.arasaka.oishii.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.arasaka.oishii.domain.model.Category
import com.arasaka.oishii.domain.model.Meal
import com.arasaka.oishii.framework.db.CategoryDb
import com.arasaka.oishii.framework.db.MealDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            //database.execSQL("ALTER TABLE Meal ADD COLUMN alcoholic TEXT")
            //database.execSQL("CREATE TABLE `Category`(`idCategory` INTEGER PRIMARY KEY, `nameCategory` TEXT, `descriptionCategory` TEXT, `strCategoryThumb` TEXT, `token` TEXT)")
        }
    }

    @Provides
    @Singleton
    fun provideMealDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MealDb::class.java, "meals").build()

    @Provides
    @Singleton
    fun provideCategoryDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CategoryDb::class.java, "categories").build()


}