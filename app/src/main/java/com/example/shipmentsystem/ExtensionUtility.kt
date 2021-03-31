package com.example.shipmentsystem

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
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

fun Fragment.getNavController(): NavController {
    val navHostFragment = requireActivity().supportFragmentManager
        .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    return navHostFragment.navController

}//set NavHostFragment, a container for fragments

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