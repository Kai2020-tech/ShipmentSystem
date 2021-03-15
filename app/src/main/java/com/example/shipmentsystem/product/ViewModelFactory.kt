package com.example.shipmentsystem.product

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shipmentsystem.OrderVm
import com.example.shipmentsystem.order.OrderListVm

class ProductViewModelFactory(val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductVm::class.java)) {
            return ProductVm(application) as T
        }
        throw IllegalArgumentException("Unknown VewModel class.")
    }
}

class OrderViewModelFactory(val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderVm::class.java)) {
            return OrderVm(application) as T
        } else if (modelClass.isAssignableFrom(OrderListVm::class.java)) {
            return OrderListVm(application) as T
        }

        throw IllegalArgumentException("Unknown VewModel class.")
    }
}