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

    private val _processingList = MutableLiveData<List<ProcessingItem>>()
    val processingList: LiveData<List<ProcessingItem>>
        get() = _processingList

    init {
        getList()
        Timber.d("Processing VM created.")
    }


    fun getList() {
        viewModelScope.launch{
            getProcessingList()
        }
    }

    private suspend fun getProcessingList() {
        _processingList.value = dbDao.getAllProcessing()
    }
}