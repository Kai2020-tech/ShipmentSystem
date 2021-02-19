package com.example.shipmentsystem.item

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private var itemDb: ItemDatabase = ItemDatabase.getInstance(application)

    fun getAllItem() = itemDb.itemDao.getAllItems()
}