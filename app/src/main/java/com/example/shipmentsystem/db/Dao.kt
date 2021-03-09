package com.example.shipmentsystem.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {
    @Insert
    fun insert(product: Product)

    @Insert
    fun insertOrder(orderItem: OrderItem)

    @Update
    fun update(product: Product)

    @Query("SELECT * FROM product_table WHERE id = :key")
    fun get(key: Int): Product?

    //清除全部資料表
    @Query("DELETE FROM product_table")
    fun clear()

    //清除選定資料
    @Query("DELETE FROM product_table WHERE id = :key")
    fun delete(key: Int)

    //依id將資料表做降序(descending大到小),LIMIT 1表示只回傳一個項目
    //getItem()回傳可空的Item,因爲資料表在一開始與全部清除時,都會是空的
    @Query("SELECT * FROM product_table ORDER BY id DESC LIMIT 1")
    fun getItem(): Product?

    @Query("SELECT * FROM product_table  ORDER BY id DESC")
    fun getAllProducts(): List<Product>
}