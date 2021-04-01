package com.example.shipmentsystem.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.*

@Dao
interface Dao {
    /** Product */
    @Insert
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun update(product: Product)

    @Query("SELECT * FROM product_table WHERE id = :key")
    suspend fun get(key: Int): Product?

    //清除全部資料表
    @Query("DELETE FROM product_table")
    suspend fun clear()

    //清除選定資料
    @Query("DELETE FROM product_table WHERE id = :key")
    suspend fun delete(key: Int)

    //依id將資料表做降序(descending大到小),LIMIT 1表示只回傳一個項目
    //getItem()回傳可空的Item,因爲資料表在一開始與全部清除時,都會是空的
    @Query("SELECT * FROM product_table ORDER BY id DESC LIMIT 1")
    suspend fun getItem(): Product?

    @Query("SELECT * FROM product_table  ORDER BY id DESC")
    suspend fun getAllProducts(): List<Product>

    /** Processing */
    @Insert
    suspend fun insertProcessingItem(item: ProcessingItem)

//return LiveData will force shipFragment updated
//    @Query("SELECT * FROM processing_table  ORDER BY id DESC")
//    fun getAllProcessingItem(): LiveData<List<ProcessingItem>>

    @Query("SELECT * FROM processing_table  ORDER BY id DESC")
    suspend fun getAllProcessingItem(): List<ProcessingItem>

    //清除選定資料
    @Query("DELETE FROM processing_table WHERE id = :key")
    suspend fun deleteProcessingItem(key: Int)

    /** Edit VM commit */
    @Update
    suspend fun updateProcessingItem(item: ProcessingItem)

    /** Ship search */
    @Query("SELECT * FROM processing_table  WHERE order_date >= :startDate AND order_date <= :endDate")
    fun searchOrderList(startDate: Date, endDate: Date): LiveData<List<ProcessingItem>>

    /** Complete */
    @Insert
    suspend fun insertCompleteItem(item: CompleteItem)

    @Query("SELECT * FROM complete_table  ORDER BY id DESC")
    fun getAllCompleteItem(): LiveData<List<CompleteItem>>


}