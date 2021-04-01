package com.example.shipmentsystem.ship.complete

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.shipmentsystem.db.CompleteItem
import com.example.shipmentsystem.db.MyDatabase

class CompleteVm(application: Application) : AndroidViewModel(application) {
    private val app = application
    private var dbDao = MyDatabase.getInstance(application).dao

    val completeList: LiveData<List<CompleteItem>> = dbDao.getAllCompleteItem()
}