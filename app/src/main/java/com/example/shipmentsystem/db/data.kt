package com.example.shipmentsystem.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
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
@Parcelize
data class ProcessingItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "customer_name")
    var name: String = "",

    @ColumnInfo(name = "order_date")
    var date: Date,

    @ColumnInfo(name = "order_productList")
    var orderList: MutableList<OrderItem>,

    @ColumnInfo(name = "order_totalPrice")
    var totalPrice: Int = 0
) : Parcelable

@Entity(tableName = "complete_table")
data class CompleteItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "customer_name")
    var name: String = "",

    @ColumnInfo(name = "order_date")
    var orderDate: Date,

    @ColumnInfo(name = "complete_date")
    var completeDate: Date,

    @ColumnInfo(name = "order_productList")
    var orderList: MutableList<OrderItem>,

    @ColumnInfo(name = "order_totalPrice")
    var totalPrice: Int = 0
)

@Parcelize
data class OrderItem(
    val name: String,
    val amount: Int,
    val sumPrice: Int,
    var isChecked: Boolean = false
) : Parcelable
