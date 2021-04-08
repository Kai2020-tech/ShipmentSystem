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

    var searchOrderDate = ""

    private lateinit var _searchResultProcessingList: LiveData<List<ProcessingItem>>
    val searchResultProcessingList: LiveData<List<ProcessingItem>>
        get() = _searchResultProcessingList


    fun getSearchDate(start: Date, end: Date) {

        _searchResultProcessingList = dbDao.getSearchProcessingList(start, end)

        val startDate = SimpleDateFormat("yyyy/MM/dd").format(start)
        val endDate = SimpleDateFormat("yyyy/MM/dd").format(end)
        searchOrderDate = "$startDate ~ $endDate"
    }
}