package com.arasaka.oishii.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arasaka.oishii.domain.model.User


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDb: RoomDatabase() {
    //abstract fun userDao(): userDao
}