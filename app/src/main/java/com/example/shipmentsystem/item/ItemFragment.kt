package com.example.shipmentsystem.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentItemBinding


class ItemFragment : Fragment() {
    private lateinit var itemBinding: FragmentItemBinding
    private lateinit var itemDb: ItemDatabase
    private lateinit var itemRvAdapter: RvItemAdapter
    private var itemList = mutableListOf<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemDb = ItemDatabase.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        itemBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_item, container, false)

        itemRvAdapter = RvItemAdapter()
        itemBinding.rvItem.adapter = itemRvAdapter
        itemBinding.rvItem.layoutManager = LinearLayoutManager(requireActivity())

        if (itemDb != null) {
            itemList = itemDb.itemDao.getAllItems().toMutableList()
            itemRvAdapter.update(itemList)
        }

        itemBinding.btnCreate.setOnClickListener {
            val name = itemBinding.edItemName
            val price = itemBinding.edItemPrice


            val item = Item(
            name.text.toString(),
            price.text.toString().toInt()
        )
            itemDb.itemDao.insert(item)
            itemList.add(item)
            itemRvAdapter.update(itemList)
        }



        return itemBinding.root
    }


}