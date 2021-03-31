package com.example.shipmentsystem.ship.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.shipmentsystem.db.MyDatabase
import com.example.shipmentsystem.db.ProcessingItem
import java.util.*

class SearchVm(application: Application) : AndroidViewModel(application) {
    private var dbDao = MyDatabase.getInstance(application).dao

    private lateinit var _searchList: LiveData<List<ProcessingItem>>
    val searchList: LiveData<List<ProcessingItem>>
        get() = _searchList


    fun getSearchDate(start: Date, end: Date) {
        _searchList = dbDao.searchOrderList(start, end)
    }
}