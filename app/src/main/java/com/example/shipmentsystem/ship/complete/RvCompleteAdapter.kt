package com.example.shipmentsystem.ship.complete

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shipmentsystem.databinding.ItemCompleteBinding
import com.example.shipmentsystem.db.CompleteItem
import com.example.shipmentsystem.db.ProcessingItem
import com.example.shipmentsystem.ship.processing.RvProcessingAdapter
import java.text.SimpleDateFormat

class RvCompleteAdapter : RecyclerView.Adapter<RvCompleteAdapter.MyHolder>() {

    var itemClickListener: (CompleteItem) -> Unit = {}

    private val innerList = mutableListOf<CompleteItem>()


    inner class MyHolder(binding: ItemCompleteBinding) : RecyclerView.ViewHolder(binding.root) {
        var orderDate = binding.tvOrderDate
        var completeDate = binding.tvCompleteDate
        var name = binding.tvCustomerName
        var itemCount = binding.tvItemCount
        var totalPrice = binding.tvTotalPrice

        fun bind(holder: MyHolder, position: Int) {
            val currentItem = innerList[position]
            holder.orderDate.text = SimpleDateFormat("yyyy/MM/dd").format(currentItem.orderDate)
            holder.completeDate.text = SimpleDateFormat("yyyy/MM/dd").format(currentItem.completeDate)
            holder.name.text = currentItem.name
            holder.itemCount.text = currentItem.orderList.size.toString()
            holder.totalPrice.text = currentItem.totalPrice.toString()

            holder.itemView.setOnClickListener {
                itemClickListener.invoke(innerList[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            ItemCompleteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(holder, position)
    }

    override fun getItemCount() = innerList.size

    fun update(updateList: List<CompleteItem>) {
        innerList.clear()
        innerList.addAll(updateList)
        this.notifyDataSetChanged()
    }
}