package com.example.shipmentsystem.ship.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shipmentsystem.db.MyDatabase
import com.example.shipmentsystem.db.ProcessingItem
import kotlinx.coroutines.launch
import java.util.*

class SearchVm(application: Application) : AndroidViewModel(application) {
    private var dbDao = MyDatabase.getInstance(application).dao
//    lateinit var startDate: Date
//    lateinit var endDate: Date

    private lateinit var _searchList: LiveData<List<ProcessingItem>>
    val searchList: LiveData<List<ProcessingItem>>
        get() = _searchList


    fun getSearchDate(start: Date, end: Date) {
//        startDate = start
//        endDate = end
        _searchList = dbDao.searchOrderList(start, end)
    }
}