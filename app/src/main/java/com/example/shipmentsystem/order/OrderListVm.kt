package com.example.shipmentsystem.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.shipmentsystem.db.OrderItem
import timber.log.Timber
import java.text.FieldPosition

class OrderListVm(application: Application) : AndroidViewModel(application) {
    val orderList = MutableLiveData<MutableList<OrderItem>>()
    val selectedItem = MutableLiveData<OrderItem>()
    private val list = mutableListOf<OrderItem>()
    val customerName = MutableLiveData<String>()

    init {
        Timber.d("OrderListVm created.")
    }

    fun createOrderItem(item: OrderItem){
        list.add(item)
        orderList.value = list
        print("${orderList.value}")
    }

    fun onSelectedOrderItem(item: OrderItem,id: Int){
        selectedItem.value = item
    }


}