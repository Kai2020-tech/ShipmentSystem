package com.example.shipmentsystem.product

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shipmentsystem.order.OrderListVm
import com.example.shipmentsystem.ship.ProcessingVm

class ProductViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductVm::class.java)) {
            return ProductVm(application) as T
        }
        throw IllegalArgumentException("Unknown VewModel class.")
    }
}

class ViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ProductVm::class.java)) {
//            return ProductVm(application) as T
//        } else if (modelClass.isAssignableFrom(OrderListVm::class.java)) {
//            return OrderListVm(application) as T
//        }

        when{
            modelClass.isAssignableFrom(ProductVm::class.java) ->{
                return ProductVm(application) as T
            }
            modelClass.isAssignableFrom(OrderListVm::class.java) ->{
                return OrderListVm(application) as T
            }
            modelClass.isAssignableFrom(ProcessingVm::class.java) ->{
                return ProcessingVm(application) as T
            }
        }

        throw IllegalArgumentException("Unknown VewModel class.")
    }
}