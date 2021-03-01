package com.example.shipmentsystem.item

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.shipmentsystem.databinding.ItemViewBinding

class RvItemAdapter : RecyclerView.Adapter<RvItemAdapter.MyHolder>() {
    private val innerItemList = mutableListOf<Item>()
    var itemClickListener: (Item) -> Unit = {}
    lateinit var itemVM: ItemViewModel
    lateinit var lifecycleOwner: LifecycleOwner
    private val selectedColor = "#F57C00"
    private val unSelectedColor = "#FFAB91"

    inner class MyHolder(binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.tvItemName
        var price = binding.tvItemPrice
        var id = binding.tvItemId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val myHolder = MyHolder(
            ItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
        myHolder.itemView.setOnClickListener {
            itemClickListener.invoke(innerItemList[myHolder.adapterPosition])
        }

        return myHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentItem = innerItemList[position]
        holder.id.text = currentItem.id.toString()
        holder.name.text = currentItem.name
        holder.price.text = "$ ${currentItem.price}"

        setSelectedItemBackground(currentItem, holder)
    }

    override fun getItemCount(): Int = innerItemList.size

    private fun setSelectedItemBackground(currentItem: Item, holder: MyHolder) {
        itemVM.selectedItem.observe(lifecycleOwner, Observer {
            if (currentItem.id == itemVM.selectedItem.value?.id) {
                holder.itemView.setBackgroundColor(Color.parseColor(selectedColor))
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor(unSelectedColor))
            }
        })
    }

    private fun observeItemList() {
        itemVM.itemList.observe(lifecycleOwner, Observer {
            update(it)
        })
    }

    fun update(updateList: List<Item>) {
        innerItemList.clear()
        innerItemList.addAll(updateList)
        this.notifyDataSetChanged()
    }

    fun getVM(vm: ItemViewModel, owner: LifecycleOwner) {
        itemVM = vm
        lifecycleOwner = owner
        observeItemList()
    }
}