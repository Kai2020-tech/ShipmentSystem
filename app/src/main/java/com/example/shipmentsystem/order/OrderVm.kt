package com.example.shipmentsystem

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.shipmentsystem.db.MyDatabase
import com.example.shipmentsystem.db.OrderItem

class OrderVm(application: Application) : AndroidViewModel(application) {
    private var db: MyDatabase = MyDatabase.getInstance(application)

    fun createOrder() {
        val date = 100030
        val item = OrderItem("adf",123)
        db.dao.insertOrder(item)
    }
}