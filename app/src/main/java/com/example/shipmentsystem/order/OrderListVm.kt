package com.example.shipmentsystem.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.shipmentsystem.db.OrderItem
import com.example.shipmentsystem.toast
import timber.log.Timber

class OrderListVm(application: Application) : AndroidViewModel(application) {
    val orderList = MutableLiveData<MutableList<OrderItem>>()
    val selectedItem = MutableLiveData<OrderItem>()
    private var onSelected = true
    private val list = mutableListOf<OrderItem>()
    val customerName = MutableLiveData<String>()

    init {
        Timber.d("OrderListVm created.")
    }

    fun createOrderItem(item: OrderItem) {
        list.add(item)
        orderList.value = list
        print("${orderList.value}")
    }

    fun onSelectedOrderItem(item: OrderItem) {
        when {
            onSelected -> {
                setSelectedItemValue(item, onSelected)
                toast("${item.name} \n selected.")
                onSelected = false
            }
            item === selectedItem.value -> {
                setSelectedItemValue(item, onSelected)
                toast("${item.name} \n unselected.")
                onSelected = true
            }
            else -> {
                setSelectedItemValue(item, true)
                toast("${item.name} \n selected.")
                onSelected = false
            }
        }
    }

    private fun setSelectedItemValue(item: OrderItem, isSelected: Boolean) {
        if (isSelected) {
            selectedItem.value = item
        } else {
            selectedItem.value = null
        }
    }


}