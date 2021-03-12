package com.example.shipmentsystem.product

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shipmentsystem.db.MyDatabase
import com.example.shipmentsystem.db.Product
import kotlinx.coroutines.launch
import timber.log.Timber

class ProductVM(application: Application) : AndroidViewModel(application) {
    private var db: MyDatabase = MyDatabase.getInstance(application)
    var selectedProduct = MutableLiveData<Product>()
    val productList = MutableLiveData<List<Product>>()
    private var onSelected = true
    private val app = application

    init {
        Timber.d("ProductViewModel created! $this")
        onCreateProductList()
    }

    fun onCreateProductList() {
        viewModelScope.launch {
            getProductList()
        }
    }

    private suspend fun getProductList() {
        productList.value = db.dao.getAllProducts()
    }

    fun onInsertProduct(name: String, price: Int) {
        viewModelScope.launch {
            val product = Product(name, price)
            insertProductItem(product)
            onCreateProductList()
        }
    }

    private suspend fun insertProductItem(product: Product) {
        db.dao.insertProduct(product)
    }

    //    fun get(itemSelectedId: Int): Item? = itemDb.itemDao.get(itemSelectedId)


    fun onDeleteProduct(itemSelectedId: Int) {
        viewModelScope.launch {
            deleteProduct(itemSelectedId)
            selectedProduct.value = null
            onCreateProductList()
        }
    }

    private suspend fun deleteProduct(itemSelectedId: Int) {
        db.dao.delete(itemSelectedId)
    }

    fun onUpdate(product: Product) {
        viewModelScope.launch {
            update(product)
            onCreateProductList()
            selectedProduct.value = null
        }
    }

    private suspend fun update(product: Product) {
        db.dao.update(product)
    }

    fun onDbClear() {
        viewModelScope.launch {
            dbClear()
            selectedProduct.value = null
            onCreateProductList()
        }
    }
    private suspend fun dbClear(){
        db.dao.clear()
    }

    fun onSelectProduct(product: Product) {
        viewModelScope.launch {
            selectProduct(product)
        }
    }

    private suspend fun selectProduct(product: Product){
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

    private suspend fun setSelectedProductValue(itemSelectedId: Int, isSelected: Boolean) {
        if (isSelected) {
            selectedProduct.value = db.dao.get(itemSelectedId)
        } else {
            selectedProduct.value = null
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
        Timber.d("ProductViewModel destroyed!")
    }
}