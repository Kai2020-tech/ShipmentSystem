package com.example.shipmentsystem.product

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentProductBinding
import com.example.shipmentsystem.db.Product
import com.example.shipmentsystem.getProductViewModel
import timber.log.Timber


class ProductFragment : Fragment() {
    private var productBinding: FragmentProductBinding? = null
    private val binding get() = productBinding!!

    private lateinit var productRvAdapter: RvItemAdapter

    private lateinit var productViewModel: ProductViewModel

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

        productViewModel = getProductViewModel()
        productViewModel.getAllProduct()

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
            productViewModel.createProduct(name, price)
            toast(getString(R.string.created, name))
            clearEditText()
        }
        /** Delete */
        binding.btnDelete.setOnClickListener {
            val selectedItem = productViewModel.selectedProduct.value
            selectedItem?.let {
                productViewModel.deleteProduct(it.id)
                toast(getString(R.string.deleted, it.name))
            } ?: let {
                toast(getString(R.string.pleaseSelect))
            }
        }
        /** Update */
        binding.btnUpdate.setOnClickListener {
            val selectedItem = productViewModel.selectedProduct.value
            selectedItem?.let {
                it.name = productName.text.toString()
                it.price = productPrice.text.toString().toInt()
                productViewModel.update(it)
                toast(getString(R.string.updated, it.name))
            } ?: let {
                toast(getString(R.string.pleaseSelect))
            }
        }
        /** Query */
        binding.btnQuery.setOnClickListener {
            val name = productName.text.toString()
            productRvAdapter.update(productViewModel.query(name))

            hideKeyboard(binding.textView)
        }
    }

    private fun initProductRecyclerView() {
        productRvAdapter = RvItemAdapter()
        binding.rvProduct.adapter = productRvAdapter.apply {
            itemClickListener = {
                productViewModel.selectProduct(it)
            }

            changeBackgroundListener = { currentItem, holder ->
                setSelectedItemBackground(currentItem, holder)
            }
        }
        binding.rvProduct.layoutManager = LinearLayoutManager(requireActivity())
        productViewModel.productList.observe(viewLifecycleOwner, Observer {
            productRvAdapter.update(it)
        })
    }

    private fun setSelectedItemBackground(currentProduct: Product, holder: RvItemAdapter.MyHolder) {
        val selectedColor = getString(R.string.selectedColor)
        val defaultColor = getString(R.string.defaultColor)
        productViewModel.selectedProduct.observe(viewLifecycleOwner, Observer {
            //do not use "it" in here, cause it might be null
            if (currentProduct.id == productViewModel.selectedProduct.value?.id) {
                holder.itemView.setBackgroundColor(Color.parseColor(selectedColor))
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor(defaultColor))
            }
        })
    }

    private fun setEditTextContent() {
        productViewModel.selectedProduct.observe(viewLifecycleOwner, { selectedItem ->
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
                productViewModel.dbClear()
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

    private fun toast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT)
            .show()
    }

    private fun hideKeyboard(view: View, nextFoocusView: View = view.rootView) {
        val imm = view.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
        nextFoocusView.requestFocus()
    }
}