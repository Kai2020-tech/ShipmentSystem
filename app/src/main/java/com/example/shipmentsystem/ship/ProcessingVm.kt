package com.example.shipmentsystem.ship

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shipmentsystem.db.MyDatabase
import com.example.shipmentsystem.db.ProcessingItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class ProcessingVm(application: Application) : AndroidViewModel(application) {
    private var dbDao = MyDatabase.getInstance(application).dao

    private val _processingList: LiveData<List<ProcessingItem>> = getList()
    val processingList: LiveData<List<ProcessingItem>>
        get() = _processingList

    init {
        Timber.d("Processing VM created.")
    }

    private fun getList():LiveData<List<ProcessingItem>>{
        return dbDao.getAllProcessing()
    }

}