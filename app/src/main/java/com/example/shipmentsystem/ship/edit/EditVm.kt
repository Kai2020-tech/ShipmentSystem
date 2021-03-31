package com.example.shipmentsystem.ship.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shipmentsystem.R
import com.example.shipmentsystem.db.MyDatabase
import com.example.shipmentsystem.db.OrderItem
import com.example.shipmentsystem.db.ProcessingItem
import com.example.shipmentsystem.toast
import kotlinx.coroutines.launch
import java.util.*

class EditVm(application: Application) : AndroidViewModel(application) {
    private val app = application
    private var dbDao = MyDatabase.getInstance(application).dao

    private val _processingItem = MutableLiveData<ProcessingItem?>()
    val processingItem: LiveData<ProcessingItem?>
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

    private val _updatedItem = MutableLiveData<OrderItem>()
    val updatedItem: LiveData<OrderItem>
        get() = _updatedItem

    private val _totalOrderPrice = MutableLiveData<Int>()
    val totalOrderPrice: LiveData<Int>
        get() = _totalOrderPrice

    fun getProcessingItem(item: ProcessingItem) {
        _processingItem.value = item
        _orderList.value = _processingItem.value?.orderList
        _totalOrderPrice.value = item.totalPrice
    }

    fun createOrderItem(item: OrderItem) {
        if (_orderList.value?.size == 0) {
            _processingItem.value?.orderList?.clear()
        }
        _processingItem.value?.orderList?.add(item)
        _orderList.value = _processingItem.value?.orderList
        calTotalOrderPrice()
    }


    fun deleteOrderItem() {
        if (_selectedItem.value != null) {
            _processingItem.value?.orderList?.remove(selectedItem.value)
            _orderList.value = _processingItem.value?.orderList
            _selectedItem.value = null
            calTotalOrderPrice()
        } else {
            toast(app.getString(R.string.please_select_an_item))
        }
    }

    fun updateOrderItem(item: OrderItem) {
        _updatedItem.value = item
        _processingItem.value?.orderList?.set(selectedPos.value ?: -1, item)
        calTotalOrderPrice()
    }

    fun updateProcessingItem(name: String, date: Date, list: MutableList<OrderItem>) {
        val item = _processingItem.value
        item?.let {
            it.name = name
            it.date = date
            it.orderList = list
            it.totalPrice = _totalOrderPrice.value ?: 0
            viewModelScope.launch {
                updateProcessingItem(it)
            }
            toast(app.getString(R.string.order_saved, item.name))
        }

    }

    private suspend fun updateProcessingItem(item: ProcessingItem) {
        dbDao.updateProcessingItem(item)
    }

    fun onDelete() {
        viewModelScope.launch {
            _processingItem.value?.id?.let { deleteProcessingItem(it) }
        }
    }

    private suspend fun deleteProcessingItem(key: Int) {
        dbDao.deleteProcessingItem(key)
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

    fun onClear() {
        _processingItem.value?.orderList?.clear()
        _orderList.value = _processingItem.value?.orderList
        _totalOrderPrice.value = 0
    }
}