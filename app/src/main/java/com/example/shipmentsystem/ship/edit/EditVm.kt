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

    private val list = mutableListOf<OrderItem>()

    fun getProcessingItem(item: ProcessingItem) {
        _processingItem.value = item
        list.addAll(item.orderList)
    }

    fun createOrderItem(item: OrderItem) {
        list.add(item)
        _orderList.value = list
        calTotalOrderPrice()
    }

    private fun calTotalOrderPrice() {
        _totalOrderPrice.value = 0
        list.forEach {
            _totalOrderPrice.value = _totalOrderPrice.value!! + it.sumPrice
        }
    }
}