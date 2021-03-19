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

    private val _updatedItem = MutableLiveData<OrderItem>()
    val updatedItem: LiveData<OrderItem>
        get() = _updatedItem

    private val _selectedPos = MutableLiveData<Int>()
    val selectedPos: LiveData<Int>
        get() = _selectedPos

    private val _totalOrderPrice = MutableLiveData<Int>()
    val totalOrderPrice: LiveData<Int>
        get() = _totalOrderPrice

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
        calTotalOrderPrice()
        print("${orderList.value}")
    }

    fun onSelectedOrderItem(item: OrderItem, pos: Int) {
        when {
            onSelected -> {
                setSelectedItemValue(item, onSelected)
                onSelected = false
                _selectedPos.value = pos
                toast("${item.name} \n selected. ${_selectedPos.value}")
            }
            item === selectedItem.value -> {
                setSelectedItemValue(item, onSelected)
                onSelected = true
                _selectedPos.value = null
                toast("${item.name} \n unselected.")
            }
            else -> {
                setSelectedItemValue(item, true)
                onSelected = false
                _selectedPos.value = pos
                toast("${item.name} \n selected. ${_selectedPos.value}")
            }
        }
    }

    fun deleteOrderItem() {
        if (_selectedItem.value != null) {
            list.remove(selectedItem.value)
            _orderList.value = list
            _selectedItem.value = null
            calTotalOrderPrice()
        } else {
            toast(app.getString(R.string.please_select_an_item))
        }
    }

    fun updateOrderItem(item: OrderItem) {
        _updatedItem.value = item
        list[selectedPos.value ?: -1] = item
        calTotalOrderPrice()
    }

    private fun setSelectedItemValue(item: OrderItem, isSelected: Boolean) {
        if (isSelected) {
            _selectedItem.value = item
        } else {
            _selectedItem.value = null
        }
    }

    private fun calTotalOrderPrice(){
        _totalOrderPrice.value = 0
        list.forEach {
            _totalOrderPrice.value = _totalOrderPrice.value!! + it.sumPrice
        }
    }


}