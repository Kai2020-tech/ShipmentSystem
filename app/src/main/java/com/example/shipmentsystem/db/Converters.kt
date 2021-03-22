package com.example.shipmentsystem.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun productListFromJson(value: String): MutableList<Product> {
        val listType = object : TypeToken<MutableList<Product>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun productListToJson(list: MutableList<Product>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

}