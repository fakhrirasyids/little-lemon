package com.fakhrirasyids.littlelemon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MenuItemRoom::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao
}
