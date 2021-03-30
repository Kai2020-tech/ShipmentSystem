package com.example.shipmentsystem.ship.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shipmentsystem.db.OrderItem
import com.example.shipmentsystem.db.ProcessingItem
import timber.log.Timber

class EditVm(application: Application) : AndroidViewModel(application) {
    private val _processingItem = MutableLiveData<ProcessingItem>()
    val processingItem: LiveData<ProcessingItem>
        get() = _processingItem

    private val _orderList = MutableLiveData<MutableList<OrderItem>>()
    val orderList: LiveData<MutableList<OrderItem>>
        get() = _orderList

    private val _totalOrderPrice = MutableLiveData<Int>()
    val totalOrderPrice: LiveData<Int>
        get() = _totalOrderPrice

    fun getProcessingItem(item: ProcessingItem) {
        _processingItem.value = item
    }

    fun createOrderItem(item: OrderItem) {
        _processingItem.value?.orderList?.add(item)
        _orderList.value = _processingItem.value?.orderList
        calTotalOrderPrice()
    }

    private fun calTotalOrderPrice() {
        _totalOrderPrice.value = 0
        _processingItem.value?.orderList?.forEach {
            _totalOrderPrice.value = _totalOrderPrice.value!! + it.sumPrice
        }
    }
}