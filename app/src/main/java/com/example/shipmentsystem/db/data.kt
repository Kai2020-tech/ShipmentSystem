package com.example.shipmentsystem.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
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

@Parcelize
data class OrderItem(
    val name: String,
    val amount: Int,
    val sumPrice: Int
) : Parcelable
