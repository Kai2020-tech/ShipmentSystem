package com.example.shipmentsystem.product

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.shipmentsystem.db.MyDatabase
import com.example.shipmentsystem.db.Product
import timber.log.Timber

class ProductVM(application: Application) : AndroidViewModel(application) {
    private var db: MyDatabase = MyDatabase.getInstance(application)
    var selectedProduct = MutableLiveData<Product>()
    val productList = MutableLiveData<List<Product>>()
    var onSelected = true
    private val app = application

    init {
        Timber.d("GameViewModel created! $this")
    }

    fun getAllProduct() {
        productList.value = db.dao.getAllProducts()
    }

    fun createProduct(name: String, price: Int) {
        val item = Product(name, price)
        db.dao.insert(item)
        getAllProduct()
    }

    //    fun get(itemSelectedId: Int): Item? = itemDb.itemDao.get(itemSelectedId)

    private fun setSelectedProductValue(itemSelectedId: Int, isSelected: Boolean) {
        if (isSelected) {
            selectedProduct.value = db.dao.get(itemSelectedId)
        } else {
            selectedProduct.value = null
        }
    }

    fun deleteProduct(itemSelectedId: Int) {
        db.dao.delete(itemSelectedId)
        selectedProduct.value = null
        getAllProduct()
    }

    fun update(product: Product) {
        db.dao.update(product)
        getAllProduct()
        selectedProduct.value = null
    }

    fun dbClear() {
        db.dao.clear()
        selectedProduct.value = null
        getAllProduct()
    }

    fun selectProduct(product: Product) {
        when {
            onSelected -> {
                setSelectedProductValue(product.id, onSelected)
                toast("${product.name} \n selected.")
                onSelected = false
            }
            product.id == selectedProduct.value?.id -> {
                setSelectedProductValue(product.id, onSelected)
                toast("${product.name} \n unselected.")
                onSelected = true
            }
            else -> {
                setSelectedProductValue(product.id, true)
                toast("${product.name} \n selected.")
                onSelected = false
            }
        }
    }

    fun query(name: String): List<Product> {
        val resultList = mutableListOf<Product>()
        if (name.isNotEmpty()) {
            productList.value?.forEach {
                if (it.name.contains(name)) {
                    resultList.add(it)
                }
            }
            return if (resultList.isEmpty()) {
                toast("not found $name")
                productList.value!!
            } else {
                resultList
            }
        } else {
            toast("Please enter a name")
            return productList.value!!
        }
    }

    private fun toast(message: String) {
        Toast.makeText(app, message, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("GameViewModel destroyed!")
    }
}