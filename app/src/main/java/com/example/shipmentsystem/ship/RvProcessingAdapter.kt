package com.example.shipmentsystem.ship

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shipmentsystem.databinding.ItemShipBinding
import com.example.shipmentsystem.db.ProcessingItem
import java.text.SimpleDateFormat

class RvProcessingAdapter : RecyclerView.Adapter<RvProcessingAdapter.MyHolder>() {

    private val innerList = mutableListOf<ProcessingItem>()

    inner class MyHolder(binding: ItemShipBinding) : RecyclerView.ViewHolder(binding.root) {
        var date = binding.tvOrderDate
        var name = binding.tvCustomerName
        var itemCount = binding.tvItemCount
        var totalPrice = binding.tvTotalPrice


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            ItemShipBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentItem = innerList[position]
        holder.date.text = SimpleDateFormat("yyyy/MM/dd").format(currentItem.date)
        holder.name.text = currentItem.name
        holder.itemCount.text = currentItem.orderList.size.toString()
        holder.totalPrice.text = currentItem.totalPrice.toString()

    }

    override fun getItemCount(): Int {
        return innerList.size
    }

    fun update(updateList: List<ProcessingItem>) {
        innerList.clear()
        innerList.addAll(updateList)
        this.notifyDataSetChanged()
    }
}