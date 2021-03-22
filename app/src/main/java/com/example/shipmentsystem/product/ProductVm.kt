package com.example.shipmentsystem.product

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shipmentsystem.R
import com.example.shipmentsystem.db.MyDatabase
import com.example.shipmentsystem.db.Product
import kotlinx.coroutines.launch
import timber.log.Timber

class ProductVm(application: Application) : AndroidViewModel(application) {
    private var db: MyDatabase = MyDatabase.getInstance(application)

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct : LiveData<Product>
    get() = _selectedProduct

    private val _productList = MutableLiveData<List<Product>>()
    val productList :LiveData<List<Product>>
    get() = _productList



    private var onSelected = true
    private val app = application

    init {
        Timber.d("ProductViewModel created! $this")
        getList()
    }

    fun getList() {
        viewModelScope.launch {
            getProductList()
        }
    }

    private suspend fun getProductList() {
        _productList.value = db.dao.getAllProducts()
    }

    fun onInsertProduct(name: String, price: Int) {
        viewModelScope.launch {
            val product = Product(name, price)
            insertProductItem(product)
            getList()
        }
    }

    private suspend fun insertProductItem(product: Product) {
        db.dao.insertProduct(product)
    }

    //    fun get(itemSelectedId: Int): Item? = itemDb.itemDao.get(itemSelectedId)


    fun onDeleteProduct(itemSelectedId: Int) {
        viewModelScope.launch {
            deleteProduct(itemSelectedId)
            _selectedProduct.value = null
            getList()
        }
    }

    private suspend fun deleteProduct(itemSelectedId: Int) {
        db.dao.delete(itemSelectedId)
    }

    fun onUpdate(product: Product) {
        viewModelScope.launch {
            update(product)
            getList()
            _selectedProduct.value = null
        }
    }

    private suspend fun update(product: Product) {
        db.dao.update(product)
    }

    fun onDbClear() {
        viewModelScope.launch {
            dbClear()
            _selectedProduct.value = null
            getList()
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
            product.id == _selectedProduct.value?.id -> {
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
            _selectedProduct.value = db.dao.get(itemSelectedId)
        } else {
            _selectedProduct.value = null
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
            toast(app.getString(R.string.please_enter_a_name))
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