package com.example.shipmentsystem.item

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private var itemDb: ItemDatabase = ItemDatabase.getInstance(application)
    var selectedItem = MutableLiveData<Item>()

    fun getAllItem() = itemDb.itemDao.getAllItems()

    fun createItem(item: Item) {
        itemDb.itemDao.insert(item)
    }

    fun get(itemSelectedId: Int): Item? = itemDb.itemDao.get(itemSelectedId)
    fun getItem(itemSelectedId: Int) {
        selectedItem.value = itemDb.itemDao.get(itemSelectedId)
    }

    fun deleteItem(itemSelectedId: Int) {
        itemDb.itemDao.delete(itemSelectedId!!)
    }

    fun update(item: Item) {
        itemDb.itemDao.update(item)
    }

    fun clear() {
        itemDb.itemDao.clear()
    }
}