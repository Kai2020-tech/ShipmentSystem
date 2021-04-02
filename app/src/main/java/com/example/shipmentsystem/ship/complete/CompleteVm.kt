package com.example.shipmentsystem.ship.complete

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shipmentsystem.db.CompleteItem
import com.example.shipmentsystem.db.MyDatabase
import com.example.shipmentsystem.db.OrderItem

class CompleteVm(application: Application) : AndroidViewModel(application) {
    private var dbDao = MyDatabase.getInstance(application).dao

    val completeList: LiveData<List<CompleteItem>> = dbDao.getAllCompleteItem()

    private val _completeItem = MutableLiveData<CompleteItem>()
    val completeItem: LiveData<CompleteItem>
        get() = _completeItem

    fun getCompleteItem(item: CompleteItem) {
        _completeItem.value = item
    }

}