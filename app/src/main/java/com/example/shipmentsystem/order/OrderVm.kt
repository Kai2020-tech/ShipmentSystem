package com.example.shipmentsystem

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shipmentsystem.db.MyDatabase
import com.example.shipmentsystem.db.OrderItem
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*


class OrderVm(application: Application) : AndroidViewModel(application) {
    private var db: MyDatabase = MyDatabase.getInstance(application)
    val orderList = MutableLiveData<List<OrderItem>>()

    init {
        Timber.d("OrderVM created.")
    }

    fun getAllOrders() {
        viewModelScope.launch {
            getOrderList()
        }
    }

    private suspend fun getOrderList() {
        orderList.value = db.dao.getAllOrders()
    }

    fun onInsertOrder(name: String, product: String, amount: Int, date: Date) {
        viewModelScope.launch {
            val orderItem = OrderItem(name, product, amount, date)
            insertOrderItem(orderItem)
            getAllOrders()
        }
    }

    private suspend fun insertOrderItem(orderItem: OrderItem) {
        db.dao.insertOrder(orderItem)
    }
}