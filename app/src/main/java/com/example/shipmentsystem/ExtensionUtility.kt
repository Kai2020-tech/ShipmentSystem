package com.example.shipmentsystem

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shipmentsystem.order.OrderListVm
import com.example.shipmentsystem.product.OrderViewModelFactory
import com.example.shipmentsystem.product.ProductViewModelFactory
import com.example.shipmentsystem.product.ProductVm

fun Fragment.getProductViewModel(): ProductVm {
    val app = requireNotNull(activity).application
    return ViewModelProvider(
        requireActivity(),
        ProductViewModelFactory(app)
    ).get(ProductVm::class.java)
}

fun Fragment.getOrderViewModel(): OrderVm {
    val app = requireNotNull(activity).application
    return ViewModelProvider(
        requireActivity(),
        OrderViewModelFactory(app)
    ).get(OrderVm::class.java)
}

fun Fragment.getOrderListVm(): OrderListVm {
    val app = requireNotNull(activity).application
    return ViewModelProvider(
        requireActivity(),
        OrderViewModelFactory(app)
    ).get(OrderListVm::class.java)
}

fun Fragment.toast(message: String) {
    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT)
        .show()
}

fun AndroidViewModel.toast(message: String) {
    Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT)
        .show()
}

fun Fragment.hideKeyboard(view: View, nextFoocusView: View = view.rootView) {
    val imm = view.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
    view.clearFocus()
    nextFoocusView.requestFocus()
}