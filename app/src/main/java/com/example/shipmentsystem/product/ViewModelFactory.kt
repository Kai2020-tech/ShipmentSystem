package com.example.shipmentsystem.product

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shipmentsystem.OrderVm

class ProductViewModelFactory(val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductVM::class.java)) {
            return ProductVM(application) as T
        }
        throw IllegalArgumentException("Unknown VewModel class.")
    }
}

class OrderViewModelFactory(val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderVm::class.java)) {
            return OrderVm(application) as T
        }
        throw IllegalArgumentException("Unknown VewModel class.")
    }
}