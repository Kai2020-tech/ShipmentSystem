package com.example.shipmentsystem

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shipmentsystem.product.ItemViewModelFactory
import com.example.shipmentsystem.product.ProductViewModel

fun Fragment.getProductViewModel(): ProductViewModel {
    val app = requireNotNull(activity).application
    return ViewModelProvider(
        requireActivity(),
        ItemViewModelFactory(app)
    ).get(ProductViewModel::class.java)
}