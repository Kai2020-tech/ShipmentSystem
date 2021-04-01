package com.example.shipmentsystem.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.shipmentsystem.db.MyDatabase
import com.example.shipmentsystem.db.ProcessingItem
import java.text.SimpleDateFormat
import java.util.*

class SearchVm(application: Application) : AndroidViewModel(application) {
    private var dbDao = MyDatabase.getInstance(application).dao

    var searchDate = ""
    var searchCount = ""

    private lateinit var _searchList: LiveData<List<ProcessingItem>>
    val searchList: LiveData<List<ProcessingItem>>
        get() = _searchList


    fun getSearchDate(start: Date, end: Date) {

        _searchList = dbDao.searchOrderList(start, end)

        val startDate = SimpleDateFormat("yyyy/MM/dd").format(start)
        val endDate = SimpleDateFormat("yyyy/MM/dd").format(end)
        searchDate = "$startDate ~ $endDate"
    }
}