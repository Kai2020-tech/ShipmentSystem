package com.example.shipmentsystem.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "product_table")
data class Product(
    @ColumnInfo(name = "product_name")
    var name: String = "",

    @ColumnInfo(name = "price")
    var price: Int = 0,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)

@Entity(tableName = "processing_table")
data class ProcessingList(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "customer_name")
    var name: String = "",

    @ColumnInfo(name = "order_date")
    var date: Date,

    @ColumnInfo(name = "order_productList")
    var product: MutableList<Product>,

    @ColumnInfo(name = "order_totalPrice")
    var totalPrice: Int = 0
)

data class OrderItem(
    val name: String,
    val amount: Int,
    val sumPrice: Int
)
