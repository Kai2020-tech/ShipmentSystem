package com.example.shipmentsystem.item

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemDao {
    @Insert
    fun insert(item: Item)

    @Update
    fun update(item: Item)

    @Query("SELECT * FROM item_table WHERE id = :key")
    fun get(key: Int): Item?

    //清除全部資料表
    @Query("DELETE FROM item_table")
    fun clear()

    //清除選定資料
    @Query("DELETE FROM item_table")
    fun delete()

    //依id將資料表做降序(descending大到小),LIMIT 1表示只回傳一個項目
    //getItem()回傳可空的Item,因爲資料表在一開始與全部清除時,都會是空的
    @Query("SELECT * FROM item_table ORDER BY id DESC LIMIT 1")
    fun getItem(): Item?

    @Query("SELECT * FROM item_table  ORDER BY id DESC")
    fun getAllItems(): List<Item>
}