package com.example.shipmentsystem.item

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private var itemDb: ItemDatabase = ItemDatabase.getInstance(application)
    var selectedItem = MutableLiveData<Item>()
    val itemList = MutableLiveData<List<Item>>()
    var isSelected = true
    private val app = application

    init {
        Log.i("GameViewModel", "GameViewModel created! $this")
    }

    fun getAllItem() {
        itemList.value = itemDb.itemDao.getAllItems()
    }

    fun createItem(item: Item) {
        itemDb.itemDao.insert(item)
        getAllItem()
    }

    fun get(itemSelectedId: Int): Item? = itemDb.itemDao.get(itemSelectedId)
    fun getItem(itemSelectedId: Int, isSeleted: Boolean) {
        if (isSeleted) {
            selectedItem.value = itemDb.itemDao.get(itemSelectedId)
        } else {
            selectedItem.value = null
        }
    }

    fun deleteItem(itemSelectedId: Int) {
        itemDb.itemDao.delete(itemSelectedId)
        selectedItem.value = null
        getAllItem()
    }

    fun update(item: Item) {
        itemDb.itemDao.update(item)
        getAllItem()
        selectedItem.value = null
    }

    fun dbClear() {
        itemDb.itemDao.clear()
        selectedItem.value = null
        getAllItem()
    }

    fun selectItem(item: Item){
        when {
            isSelected -> {
                getItem(item.id, isSelected)
                toast("${item.name} \n selected.")
                isSelected = false
            }
            item.id == selectedItem.value?.id -> {
                getItem(item.id, isSelected)
                toast("${item.name} \n unselected.")
                isSelected = true
            }
            else -> {
                isSelected = true
                getItem(item.id, isSelected)
                toast("${item.name} \n selected.")
                isSelected = false
            }
        }
    }

    private fun toast(message: String) {
        Toast.makeText(app, message, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }
}