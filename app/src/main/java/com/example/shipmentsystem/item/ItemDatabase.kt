package com.example.shipmentsystem.item

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemDatabase : RoomDatabase() {
    abstract val itemDao: ItemDao

    companion object {
        @Volatile
        private var INSTANCE: ItemDatabase? = null
        fun getInstance(context: Context): ItemDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ItemDatabase::class.java,
                        "Item_database"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
//                    如果INSTANCE是空,透過instance建立之後,再丟給INSTANCE
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}