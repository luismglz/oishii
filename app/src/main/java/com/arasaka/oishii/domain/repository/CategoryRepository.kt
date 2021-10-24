package com.arasaka.oishii.domain.repository

import com.arasaka.oishii.core.exception.Failure
import com.arasaka.oishii.core.functional.Either
import com.arasaka.oishii.data.dto.CategoriesResponse
import com.arasaka.oishii.domain.model.Category
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn


interface CategoryRepository {

    fun getCategories(name: String): Either<Failure, CategoriesResponse>


    fun saveCategories(categories: List<Category>): Either<Failure, Boolean>
}