package com.example.shipmentsystem.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.shipmentsystem.db.OrderItem
import timber.log.Timber

class OrderListVm(application: Application) : AndroidViewModel(application) {
    val OrderList = MutableLiveData<MutableList<OrderItem>>()
    val list = mutableListOf<OrderItem>()
    val customerName = MutableLiveData<String>()

    init {
        Timber.d("OrderListVm created.")
    }

    fun createOrderItem(item: OrderItem){
        list.add(item)
        OrderList.value = list
        print("${OrderList.value}")
    }


}