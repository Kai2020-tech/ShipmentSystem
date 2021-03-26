package com.example.shipmentsystem

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shipmentsystem.order.OrderListVm
import com.example.shipmentsystem.product.ViewModelFactory
import com.example.shipmentsystem.product.ProductViewModelFactory
import com.example.shipmentsystem.product.ProductVm
import com.example.shipmentsystem.ship.processing.ProcessingVm
import kotlin.reflect.KClass

//fun Fragment.getProductVm(): ProductVm {
//    val app = requireNotNull(activity).application
//    return ViewModelProvider(
//        requireActivity(),
//        ProductViewModelFactory(app)
//    ).get(ProductVm::class.java)
//}
//
//
//fun Fragment.getOrderListVm(): OrderListVm {
//    val app = requireNotNull(activity).application
//    return ViewModelProvider(
//        requireActivity(),
//        ViewModelFactory(app)
//    ).get(OrderListVm::class.java)
//}
//
//fun Fragment.getProcessingVm(): ProcessingVm {
//    val app = requireNotNull(activity).application
//    return ViewModelProvider(
//        requireActivity(),
//        ViewModelFactory(app)
//    ).get(ProcessingVm::class.java)
//}
//
////想傳入不同ViewModel class後,回傳對應的VM實例
////但目前這樣使用會得到相同class的多個實例
//inline fun <reified T : AndroidViewModel> Fragment.getViewModel(myClass: Class<T>): T {
//    val app = requireNotNull(activity).application
//    return ViewModelProvider(
//        requireActivity(),
//        ViewModelFactory(app)
//    ).get(myClass)
//}

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