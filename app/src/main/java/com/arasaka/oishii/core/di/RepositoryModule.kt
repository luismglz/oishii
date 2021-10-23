package com.arasaka.oishii.core.di

import com.arasaka.oishii.core.platform.NetworkHandler
import com.arasaka.oishii.data.api.MealApi
import com.arasaka.oishii.data.source.MealRepositoryImpl
import com.arasaka.oishii.domain.repository.MealRepository
import com.arasaka.oishii.framework.api.ApiProvider
import com.arasaka.oishii.framework.db.MealDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module

@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMealRepository(
        apiProvider: ApiProvider,
        mealDb: MealDb,
        networkHandler: NetworkHandler
    ): MealRepository = MealRepositoryImpl(apiProvider.getEndpoint(MealApi::class.java),networkHandler = networkHandler, mealDao = mealDb.mealDao())
}