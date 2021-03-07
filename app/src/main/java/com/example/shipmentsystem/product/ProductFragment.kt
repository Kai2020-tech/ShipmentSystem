package com.example.shipmentsystem.product

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentProductBinding
import com.example.shipmentsystem.db.Product
import timber.log.Timber


class ProductFragment : Fragment() {
    private lateinit var productBinding: FragmentProductBinding

    private lateinit var productRvAdapter: RvItemAdapter

    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        productBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)

        Timber.d("$this")

        initItemViewModel()

        initItemRecyclerView()

        setEditTextContent()

        setHasOptionsMenu(true)

        crud()

        return productBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("$this destroyed.")
    }

    private fun crud() {
        /** Create */
        productBinding.btnCreate.setOnClickListener {
            val name = if (productBinding.edItemName.text.isNotEmpty()) {
                productBinding.edItemName.text.toString()
            } else {//a-z 隨機4-7個字母做一字串
                ('a'..'z').map { it }.shuffled().subList(0, (4..7).random()).joinToString("")
            }
            val price = if (productBinding.edItemPrice.text.isNotEmpty()) {
                productBinding.edItemPrice.text.toString().toInt()
            } else {
                (100..900).random()
            }
            productViewModel.createProduct(name, price)
            toast(getString(R.string.created, name))
            clearEditText()
        }
        /** Delete */
        productBinding.btnDelete.setOnClickListener {
            val selectedItem = productViewModel.selectedProduct.value
            selectedItem?.let {
                productViewModel.deleteProduct(it.id)
                toast(getString(R.string.deleted, it.name))
            } ?: let {
                toast(getString(R.string.pleaseSelect))
            }
        }
        /** Update */
        productBinding.btnUpdate.setOnClickListener {
            val selectedItem = productViewModel.selectedProduct.value
            selectedItem?.let {
                it.name = productBinding.edItemName.text.toString()
                it.price = productBinding.edItemPrice.text.toString().toInt()
                productViewModel.update(it)
                toast(getString(R.string.updated, it.name))
            } ?: let {
                toast(getString(R.string.pleaseSelect))
            }
        }
        /** Query */
        productBinding.btnQuery.setOnClickListener {
            val name = productBinding.edItemName.text.toString()
            productRvAdapter.update(productViewModel.query(name))

            hideKeyboard(productBinding.textView)
        }
    }

    private fun initItemViewModel() {
        val app = requireNotNull(activity).application
        productViewModel =
            ViewModelProvider(requireActivity(), ItemViewModelFactory(app)).get(ProductViewModel::class.java)

        productViewModel.getAllProduct()
    }

    private fun initItemRecyclerView() {
        productRvAdapter = RvItemAdapter()
        productBinding.rvProduct.adapter = productRvAdapter.apply {
            itemClickListener = {
                productViewModel.selectProduct(it)
            }

            changeBackgroundListener = { currentItem, holder ->
                setSelectedItemBackground(currentItem, holder)
            }
        }
        productBinding.rvProduct.layoutManager = LinearLayoutManager(requireActivity())
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
                productBinding.edItemName.setText(selectedItem.name)
                productBinding.edItemPrice.setText(selectedItem.price.toString())
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
        productBinding.edItemName.text.clear()
        productBinding.edItemPrice.text.clear()
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