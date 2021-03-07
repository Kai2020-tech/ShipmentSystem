package com.example.shipmentsystem.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract val dao: Dao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null
        fun getInstance(context: Context): MyDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "database"
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