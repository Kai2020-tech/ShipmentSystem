package com.example.shipmentsystem.item

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
import com.example.shipmentsystem.databinding.FragmentItemBinding
import timber.log.Timber


class ItemFragment : Fragment() {
    private lateinit var itemBinding: FragmentItemBinding

    private lateinit var itemRvAdapter: RvItemAdapter

    private lateinit var itemViewModel: ItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        itemBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_item, container, false)

        Timber.d("$this")

        initItemViewModel()

        initItemRecyclerView()

        setEditTextContent()

        setHasOptionsMenu(true)

        crud()

        return itemBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("$this destroyed.")
    }

    private fun crud() {
        /** Create */
        itemBinding.btnCreate.setOnClickListener {
            val name = if (itemBinding.edItemName.text.isNotEmpty()) {
                itemBinding.edItemName.text.toString()
            } else {//a-z 隨機4-7個字母做一字串
                ('a'..'z').map { it }.shuffled().subList(0, (4..7).random()).joinToString("")
            }
            val price = if (itemBinding.edItemPrice.text.isNotEmpty()) {
                itemBinding.edItemPrice.text.toString().toInt()
            } else {
                (100..900).random()
            }
            itemViewModel.createItem(name, price)
            toast(getString(R.string.created, name))
            clearEditText()
        }
        /** Delete */
        itemBinding.btnDelete.setOnClickListener {
            val selectedItem = itemViewModel.selectedItem.value
            selectedItem?.let {
                itemViewModel.deleteItem(it.id)
                toast(getString(R.string.deleted, it.name))
            } ?: let {
                toast(getString(R.string.pleaseSelect))
            }
        }
        /** Update */
        itemBinding.btnUpdate.setOnClickListener {
            val selectedItem = itemViewModel.selectedItem.value
            selectedItem?.let {
                it.name = itemBinding.edItemName.text.toString()
                it.price = itemBinding.edItemPrice.text.toString().toInt()
                itemViewModel.update(it)
                toast(getString(R.string.updated, it.name))
            } ?: let {
                toast(getString(R.string.pleaseSelect))
            }
        }
        /** Query */
        itemBinding.btnQuery.setOnClickListener {
            val name = itemBinding.edItemName.text.toString()
            itemRvAdapter.update(itemViewModel.query(name))

            hideKeyboard(itemBinding.textView)
        }
    }

    private fun initItemViewModel() {
        val app = requireNotNull(activity).application
        itemViewModel =
            ViewModelProvider(requireActivity(), ItemViewModelFactory(app)).get(ItemViewModel::class.java)

        itemViewModel.getAllItem()
    }

    private fun initItemRecyclerView() {
        itemRvAdapter = RvItemAdapter()
        itemBinding.rvItem.adapter = itemRvAdapter.apply {
            itemClickListener = {
                itemViewModel.selectItem(it)
            }

            changeBackgroundListener = { currentItem, holder ->
                setSelectedItemBackground(currentItem, holder)
            }
        }
        itemBinding.rvItem.layoutManager = LinearLayoutManager(requireActivity())
        itemViewModel.itemList.observe(viewLifecycleOwner, Observer {
            itemRvAdapter.update(it)
        })
    }

    private fun setSelectedItemBackground(currentItem: Item, holder: RvItemAdapter.MyHolder) {
        val selectedColor = getString(R.string.selectedColor)
        val defaultColor = getString(R.string.defaultColor)
        itemViewModel.selectedItem.observe(viewLifecycleOwner, Observer {
            //do not use "it" in here, cause it might be null
            if (currentItem.id == itemViewModel.selectedItem.value?.id) {
                holder.itemView.setBackgroundColor(Color.parseColor(selectedColor))
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor(defaultColor))
            }
        })
    }

    private fun setEditTextContent() {
        itemViewModel.selectedItem.observe(viewLifecycleOwner, { selectedItem ->
            selectedItem?.let {
                itemBinding.edItemName.setText(selectedItem.name)
                itemBinding.edItemPrice.setText(selectedItem.price.toString())
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
                itemViewModel.dbClear()
                toast("All items deleted!!")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clearEditText() {
        itemBinding.edItemName.text.clear()
        itemBinding.edItemPrice.text.clear()
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