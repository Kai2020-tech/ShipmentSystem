package com.example.shipmentsystem

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shipmentsystem.db.MyDatabase
import com.example.shipmentsystem.db.OrderList
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*


class OrderVm(application: Application) : AndroidViewModel(application) {
    private var db: MyDatabase = MyDatabase.getInstance(application)
    val orderList = MutableLiveData<List<OrderList>>()
    val customerName = MutableLiveData<String>()

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

    fun onInsertOrder(name: String, product: String, sumPrice: Int, date: Date) {
        viewModelScope.launch {
            val orderItem = OrderList(name, product, sumPrice, date)
            insertOrderItem(orderItem)
            getAllOrders()
        }
    }

    private suspend fun insertOrderItem(orderList: OrderList) {
        db.dao.insertOrder(orderList)
    }

    fun setCustomerName(name: String) {
        customerName.value = name
    }
}