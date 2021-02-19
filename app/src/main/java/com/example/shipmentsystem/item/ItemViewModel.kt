package com.example.shipmentsystem.item

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private var itemDb: ItemDatabase = ItemDatabase.getInstance(application)

    fun getAllItem() = itemDb.itemDao.getAllItems()

    fun createItem(item: Item) {
        itemDb.itemDao.insert(item)
    }

    fun get(itemSelectedId: Int): Item? = itemDb.itemDao.get(itemSelectedId)

    fun deleteItem(itemSelectedId: Int) {
        itemDb.itemDao.delete(itemSelectedId!!)
    }

    fun update(item: Item){
        itemDb.itemDao.update(item)
    }

    fun clear(){
        itemDb.itemDao.clear()
    }
}