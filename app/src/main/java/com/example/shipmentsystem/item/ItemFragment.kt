package com.example.shipmentsystem.item

import android.os.Bundle
import android.view.*
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
    ): View? {
        // Inflate the layout for this fragment
        itemBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_item, container, false)

        var itemSelectedId: Int? = null

        val app = requireNotNull(activity).application
        itemViewModel =
            ViewModelProvider(this, ItemViewModelFactory(app)).get(ItemViewModel::class.java)

        itemRvAdapter = RvItemAdapter()
        itemBinding.rvItem.adapter = itemRvAdapter.apply {
            itemClickListener = {
                Toast.makeText(requireActivity(), "$it", Toast.LENGTH_SHORT).show()
                itemBinding.edItemName.setText(it.name)
                itemBinding.edItemPrice.setText(it.price.toString())
                itemSelectedId = it.id
            }
        }
        itemBinding.rvItem.layoutManager = LinearLayoutManager(requireActivity())

        refreshScreen()

        setHasOptionsMenu(true)

        /** item create */
        itemBinding.btnCreate.setOnClickListener {
            val name = if (itemBinding.edItemName.text.isNotEmpty()) {
                itemBinding.edItemName.text.toString()
            } else {
                //a-z 隨機4-7個字母做一字串
                ('a'..'z').map { it }.shuffled().subList(0, (4..7).random()).joinToString("")
            }
            val price = if (itemBinding.edItemPrice.text.isNotEmpty()) {
                itemBinding.edItemPrice.text.toString().toInt()
            } else {
                (100..900).random()
            }
            val item = Item(name, price)
            itemViewModel.createItem(item)

            itemRvAdapter.update(itemViewModel.getAllItem())

            clearEditText()
        }

        /** item delete */
        itemBinding.btnDelete.setOnClickListener {
            if (itemSelectedId != null) {
                val item = itemViewModel.get(itemSelectedId!!)
                itemViewModel.deleteItem(itemSelectedId!!)
                refreshScreen()
                Toast.makeText(requireActivity(), "${item?.name} deleted!", Toast.LENGTH_SHORT)
                    .show()
                itemSelectedId = null
            } else {
                Toast.makeText(requireActivity(), "please select an item", Toast.LENGTH_SHORT)
                    .show()
            }
            clearEditText()
        }

        /** item update */
        itemBinding.btnUpdate.setOnClickListener {
            if (itemSelectedId != null) {
                val item = itemViewModel.get(itemSelectedId!!)
                if (item != null) {
                    item.name = itemBinding.edItemName.text.toString()
                    item.price = itemBinding.edItemPrice.text.toString().toInt()
                    itemViewModel.update(item)
                    refreshScreen()
                    Toast.makeText(requireActivity(), "${item?.name} updated!", Toast.LENGTH_SHORT)
                        .show()
                }
                itemSelectedId = null
            } else {
                Toast.makeText(requireActivity(), "please select an item", Toast.LENGTH_SHORT)
                    .show()
            }
            clearEditText()
        }

        /** query */
        itemBinding.btnQuery.setOnClickListener {
            val queryList = itemViewModel.getAllItem()
            val resultList = mutableListOf<Item>()
            queryList.forEach {
                if (it.name.contains(itemBinding.edItemName.text.toString())) {
                    resultList.add(it)
                }
            }
            itemRvAdapter.update(resultList)
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
                itemViewModel.clear()
                refreshScreen()
                clearEditText()
                Toast.makeText(requireContext(), "All items deleted!!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clearEditText() {
        itemBinding.edItemName.text.clear()
        itemBinding.edItemPrice.text.clear()
    }

    private fun refreshScreen() {
        itemRvAdapter.update(itemViewModel.getAllItem())
    }
}