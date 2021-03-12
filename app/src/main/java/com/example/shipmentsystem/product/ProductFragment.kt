package com.example.shipmentsystem.product

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentProductBinding
import com.example.shipmentsystem.db.Product
import com.example.shipmentsystem.getProductViewModel
import com.example.shipmentsystem.hideKeyboard
import com.example.shipmentsystem.toast
import timber.log.Timber


class ProductFragment : Fragment() {
    private var productBinding: FragmentProductBinding? = null
    private val binding get() = productBinding!!

    private lateinit var productRvAdapter: RvProductAdapter

    private lateinit var productVM: ProductVM

    private lateinit var productName:EditText
    private lateinit var productPrice:EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        productBinding = FragmentProductBinding.inflate(inflater, container, false)
        productName = binding.edProductName
        productPrice = binding.edProductPrice

        productVM = getProductViewModel()
        productVM.onCreateProductList()

        initProductRecyclerView()

        setEditTextContent()

        setHasOptionsMenu(true)

        crud()

        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("$this destroyed.")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        productBinding = null
    }

    private fun crud() {
        /** Create */
        binding.btnCreate.setOnClickListener {
            val name = if (productName.text.isNotEmpty()) {
                productName.text.toString()
            } else {//a-z 隨機4-7個字母做一字串
                ('a'..'z').map { it }.shuffled().subList(0, (4..7).random()).joinToString("")
            }
            val price = if (productPrice.text.isNotEmpty()) {
                productPrice.text.toString().toInt()
            } else {
                (100..900).random()
            }
            productVM.onInsertProduct(name, price)
            toast(getString(R.string.created, name))
            clearEditText()
        }
        /** Delete */
        binding.btnDelete.setOnClickListener {
            val selectedItem = productVM.selectedProduct.value
            selectedItem?.let {
                productVM.onDeleteProduct(it.id)
                toast(getString(R.string.deleted, it.name))
            } ?: let {
                toast(getString(R.string.pleaseSelect))
            }
        }
        /** Update */
        binding.btnUpdate.setOnClickListener {
            val selectedItem = productVM.selectedProduct.value
            selectedItem?.let {
                it.name = productName.text.toString()
                it.price = productPrice.text.toString().toInt()
                productVM.onUpdate(it)
                toast(getString(R.string.updated, it.name))
            } ?: let {
                toast(getString(R.string.pleaseSelect))
            }
        }
        /** Query */
        binding.btnQuery.setOnClickListener {
            val name = productName.text.toString()
            productRvAdapter.update(productVM.query(name))

            hideKeyboard(binding.textView)
        }
    }

    private fun initProductRecyclerView() {
        productRvAdapter = RvProductAdapter()
        binding.rvProduct.adapter = productRvAdapter.apply {
            itemClickListener = {
                productVM.onSelectProduct(it)
            }

            changeBackgroundListener = { currentItem, holder ->
                setSelectedItemColor(currentItem, holder)
            }
        }
        binding.rvProduct.layoutManager = LinearLayoutManager(requireActivity())
        productVM.productList.observe(viewLifecycleOwner, Observer {
            productRvAdapter.update(it)
        })
    }

    private fun setSelectedItemColor(currentProduct: Product, holder: RvProductAdapter.MyHolder) {
        val selectedColor = getString(R.string.selectedProductColor)
        val defaultColor = getString(R.string.defaultProductColor)
        productVM.selectedProduct.observe(viewLifecycleOwner, Observer {
            //do not use "it" in here, cause it might be null
            if (currentProduct.id == productVM.selectedProduct.value?.id) {
                holder.name.setTextColor(Color.BLACK)
                holder.price.setTextColor(Color.BLACK)
                holder.itemView.setBackgroundColor(Color.parseColor(selectedColor))
            } else {
                holder.name.setTextColor(Color.WHITE)
                holder.price.setTextColor(Color.WHITE)
                holder.itemView.setBackgroundColor(Color.parseColor(defaultColor))
            }
        })
    }

    private fun setEditTextContent() {
        productVM.selectedProduct.observe(viewLifecycleOwner, { selectedItem ->
            selectedItem?.let {
                productName.setText(selectedItem.name)
                productPrice.setText(selectedItem.price.toString())
            }
                ?: clearEditText()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.item_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_itemListDel -> {
                productVM.onDbClear()
                toast("All items deleted!!")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clearEditText() {
        productName.text.clear()
        productPrice.text.clear()
    }

}