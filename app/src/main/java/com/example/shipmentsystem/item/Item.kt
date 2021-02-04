package com.example.shipmentsystem.item

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class Item(
    @ColumnInfo(name = "item_name")
    val name: String = "",

    @ColumnInfo(name = "price")
    val price: Int = 0,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
