package com.example.shipmentsystem.item

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentItemBinding


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

        val app = requireNotNull(activity).application
        itemViewModel =
            ViewModelProvider(this, ItemViewModelFactory(app)).get(ItemViewModel::class.java)

        itemRvAdapter = RvItemAdapter()

        itemBinding.rvItem.adapter = itemRvAdapter.apply {
            itemClickListener = {
                itemViewModel.selectItem(it)
            }
        }
        //pass VM Object to RecyclerView Adapter
        itemRvAdapter.getItemVM(itemViewModel)
        itemRvAdapter.getViewLifecycleOwner(viewLifecycleOwner)

        itemViewModel.selectedItem.observe(viewLifecycleOwner, { selectedItem ->
            selectedItem?.let {
                itemBinding.edItemName.setText(selectedItem.name)
                itemBinding.edItemPrice.setText(selectedItem.price.toString())
            }
                ?: clearEditText()
        })
        itemBinding.rvItem.layoutManager = LinearLayoutManager(requireActivity())

        itemViewModel.getAllItem()

        itemViewModel.itemList.observe(viewLifecycleOwner, {
            itemRvAdapter.update(it)
        })

        setHasOptionsMenu(true)

        /** item create */
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
            val item = Item(name, price)
            itemViewModel.createItem(item)
            clearEditText()
        }

        /** item delete */
        itemBinding.btnDelete.setOnClickListener {
            val selectedItem = itemViewModel.selectedItem.value
            selectedItem?.let {
                itemViewModel.deleteItem(it.id)
                toast("${it.name} deleted!")
            } ?: let {
                toast("please select an item")
            }
        }

        /** item update */
        itemBinding.btnUpdate.setOnClickListener {
            val selectedItem = itemViewModel.selectedItem.value
            selectedItem?.let {
                it.name = itemBinding.edItemName.text.toString()
                it.price = itemBinding.edItemPrice.text.toString().toInt()
                itemViewModel.update(it)
                toast("${it.name} updated!")
            } ?: let {
                toast("please select an item")
            }
        }

        /** query */
        itemBinding.btnQuery.setOnClickListener {
            val queryList = itemViewModel.itemList.value
            val resultList = mutableListOf<Item>()
            queryList?.forEach {
                if (it.name.contains(itemBinding.edItemName.text.toString())) {
                    resultList.add(it)
                }
            }

            itemRvAdapter.update(resultList)

            hideKeyboard(itemBinding.textView)
        }

        return itemBinding.root
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