package com.example.shipmentsystem.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shipmentsystem.R
import com.example.shipmentsystem.db.OrderItem
import com.example.shipmentsystem.toast
import timber.log.Timber

class OrderListVm(application: Application) : AndroidViewModel(application) {
    private val _orderList = MutableLiveData<MutableList<OrderItem>>()
    val orderList: LiveData<MutableList<OrderItem>>
        get() = _orderList

    private val _selectedItem = MutableLiveData<OrderItem>()
    val selectedItem: LiveData<OrderItem>
        get() = _selectedItem

    private val _selectedPos = MutableLiveData<Int>()
    val selectedPos: LiveData<Int>
        get() = _selectedPos

    private val app = application

    private var onSelected = true
    private val list = mutableListOf<OrderItem>()
    val customerName = MutableLiveData<String>()

    init {
        Timber.d("OrderListVm created.")
    }

    fun createOrderItem(item: OrderItem) {
        list.add(item)
        _orderList.value = list
        print("${orderList.value}")
    }

    fun onSelectedOrderItem(item: OrderItem,pos: Int) {
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

    fun deleteOrderItem() {
        if (_selectedItem.value != null) {
            list.remove(selectedItem.value)
            _orderList.value = list
            _selectedItem.value = null
        } else {
            toast(app.getString(R.string.please_select_an_item))
        }
    }

    fun updateOrderItem(){

    }

    private fun setSelectedItemValue(item: OrderItem, isSelected: Boolean) {
        if (isSelected) {
            _selectedItem.value = item
        } else {
            _selectedItem.value = null
        }
    }


}