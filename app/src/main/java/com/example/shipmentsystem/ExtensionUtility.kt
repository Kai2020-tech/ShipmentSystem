package com.example.shipmentsystem

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shipmentsystem.product.ItemViewModelFactory
import com.example.shipmentsystem.product.ProductVM

fun Fragment.getProductViewModel(): ProductVM {
    val app = requireNotNull(activity).application
    return ViewModelProvider(
        requireActivity(),
        ItemViewModelFactory(app)
    ).get(ProductVM::class.java)
}