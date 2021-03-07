package com.example.shipmentsystem.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(
    @ColumnInfo(name = "product_name")
    var name: String = "",

    @ColumnInfo(name = "price")
    var price: Int = 0,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)

@Entity(tableName = "orderList_table")
data class OrderItem(
    @ColumnInfo(name = "item_name")
    var name: String = "",

    @ColumnInfo(name = "price")
    var price: Int = 0,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
