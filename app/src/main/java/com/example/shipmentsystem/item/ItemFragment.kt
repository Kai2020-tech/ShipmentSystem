package com.example.shipmentsystem.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shipmentsystem.R
import com.example.shipmentsystem.databinding.FragmentItemBinding
import kotlin.random.Random


class ItemFragment : Fragment() {
    private lateinit var itemBinding: FragmentItemBinding
    private lateinit var itemDb: ItemDatabase
    private lateinit var itemRvAdapter: RvItemAdapter
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
        var itemSelectedId: Int? =null
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

        itemRvAdapter.update(itemDb.itemDao.getAllItems())

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
            itemDb.itemDao.insert(item)
            itemRvAdapter.update(itemDb.itemDao.getAllItems())

            clearEditText()
        }

        /** item delete */
        itemBinding.btnDelete.setOnClickListener {
            if(itemSelectedId != null){
                val item = itemDb.itemDao.get(itemSelectedId!!)
                itemDb.itemDao.delete(itemSelectedId!!)
                itemRvAdapter.update(itemDb.itemDao.getAllItems())
                Toast.makeText(requireActivity(), "${item?.name} deleted!", Toast.LENGTH_SHORT).show()
                itemSelectedId = null
            }else{
                Toast.makeText(requireActivity(), "please select an item", Toast.LENGTH_SHORT).show()
            }
            clearEditText()
        }

        /** item update */
        itemBinding.btnUpdate.setOnClickListener {
            if(itemSelectedId != null){
                val item = itemDb.itemDao.get(itemSelectedId!!)
                if (item != null) {
                    item.id = itemSelectedId!!.toInt()
                    item.name = itemBinding.edItemName.text.toString()
                    item.price = itemBinding.edItemPrice.text.toString().toInt()
                    itemDb.itemDao.update(item!!)
                    itemRvAdapter.update(itemDb.itemDao.getAllItems())
                    Toast.makeText(requireActivity(), "${item?.name} updated!", Toast.LENGTH_SHORT).show()
                }
                itemSelectedId = null
            }else{
                Toast.makeText(requireActivity(), "please select an item", Toast.LENGTH_SHORT).show()
            }
            clearEditText()
        }

        /** query */
        itemBinding.btnQuery.setOnClickListener {
            val queryList = itemDb.itemDao.getAllItems()
            val resultList = mutableListOf<Item>()
            queryList.forEach {
                if(it.name.contains(itemBinding.edItemName.text.toString())){
                    resultList.add(it)
                }
            }
            itemRvAdapter.update(resultList)
        }



        return itemBinding.root
    }

    private fun clearEditText() {
        itemBinding.edItemName.text.clear()
        itemBinding.edItemPrice.text.clear()
    }
}