package com.example.shipmentsystem.order

import android.app.Application
import androidx.lifecycle.*
import com.example.shipmentsystem.R
import com.example.shipmentsystem.db.MyDatabase
import com.example.shipmentsystem.db.OrderItem
import com.example.shipmentsystem.db.ProcessingItem
import com.example.shipmentsystem.db.Product
import com.example.shipmentsystem.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class OrderListVm(application: Application) : AndroidViewModel(application) {

    private var dbDao = MyDatabase.getInstance(application).dao

    private val _orderList = MutableLiveData<MutableList<OrderItem>>()
    val orderList: LiveData<MutableList<OrderItem>>
        get() = _orderList

    private val _selectedItem = MutableLiveData<OrderItem?>()
    val selectedItem: LiveData<OrderItem?>
        get() = _selectedItem

    private val _updatedItem = MutableLiveData<OrderItem>()
    val updatedItem: LiveData<OrderItem>
        get() = _updatedItem

    private val _selectedPos = MutableLiveData<Int?>()
    val selectedPos: LiveData<Int?>
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
        _orderList.value = list //let LiveData nonnull
    }

    fun createOrderItem(item: OrderItem) {
        list.add(item)
        _orderList.value = list
        calTotalOrderPrice()
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

    private fun clear() {
        list.clear()
        _orderList.value = list
        _totalOrderPrice.value = 0
    }

    private fun setSelectedItemValue(item: OrderItem, isSelected: Boolean) {
        if (isSelected) {
            _selectedItem.value = item
        } else {
            _selectedItem.value = null
        }
    }

    private fun calTotalOrderPrice() {
        _totalOrderPrice.value = 0
        list.forEach {
            _totalOrderPrice.value = _totalOrderPrice.value!! + it.sumPrice
        }
    }

    fun createProcessingItem(name: String, date: Date) {
        val item = ProcessingItem(
            name = name,
            date = date,
            orderList = list,
            totalPrice = totalOrderPrice.value ?: 0
        )
        viewModelScope.launch {
            insertProcessingItem(item)
            clear()
        }
    }

    private suspend fun insertProcessingItem(item: ProcessingItem) {
        dbDao.insertProcessing(item)
    }


}