package com.example.shipmentsystem.ship.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shipmentsystem.db.OrderItem
import com.example.shipmentsystem.db.ProcessingItem
import com.example.shipmentsystem.toast
import timber.log.Timber

class EditVm(application: Application) : AndroidViewModel(application) {
    private val _processingItem = MutableLiveData<ProcessingItem>()
    val processingItem: LiveData<ProcessingItem>
        get() = _processingItem

    private val _orderList = MutableLiveData<MutableList<OrderItem>>()
    val orderList: LiveData<MutableList<OrderItem>>
        get() = _orderList

    private val _selectedItem = MutableLiveData<OrderItem?>()
    val selectedItem: LiveData<OrderItem?>
        get() = _selectedItem

    private val _selectedPos = MutableLiveData<Int?>()
    val selectedPos: LiveData<Int?>
        get() = _selectedPos

    private var onSelected = true

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

    private fun setSelectedItemValue(item: OrderItem, isSelected: Boolean) {
        if (isSelected) {
            _selectedItem.value = item
        } else {
            _selectedItem.value = null
        }
    }
}