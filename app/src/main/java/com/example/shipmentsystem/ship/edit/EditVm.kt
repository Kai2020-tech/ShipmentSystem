package com.example.shipmentsystem.ship.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shipmentsystem.db.ProcessingItem

class EditVm(application: Application) : AndroidViewModel(application) {
    private val _processingItem = MutableLiveData<ProcessingItem>()
    val processingItem:LiveData<ProcessingItem>
    get() = _processingItem

    fun getProcessingItem(item: ProcessingItem){
        _processingItem.value = item
    }
}