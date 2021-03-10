package com.example.shipmentsystem.order

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shipmentsystem.databinding.ItemOrderBinding
import com.example.shipmentsystem.db.OrderItem

class RvOrderAdapter : RecyclerView.Adapter<RvOrderAdapter.MyHolder>() {
    private val innerItemList = mutableListOf<OrderItem>()
    var itemClickListener: (OrderItem) -> Unit = {}
    var changeBackgroundListener: (OrderItem, MyHolder) -> Unit = { orderItem: OrderItem, myHolder: MyHolder -> }

    inner class MyHolder(binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.tvItemName
        var price = binding.tvItemPrice
        var id = binding.tvItemId
        var orderDate = binding.tvOrderDate

        fun bind(orderItem: OrderItem, holder: MyHolder) {
            changeBackgroundListener.invoke(orderItem, holder)

            holder.itemView.setOnClickListener {
                itemClickListener.invoke(innerItemList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val myHolder = MyHolder(
            ItemOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

        return myHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentItem = innerItemList[position]
        holder.id.text = currentItem.id.toString()
        holder.name.text = currentItem.name
        holder.price.text = "$ ${currentItem.sumPrice}"
        holder.orderDate.text = currentItem.date.toString()
        holder.bind(currentItem, holder)
    }

    override fun getItemCount(): Int = innerItemList.size

    fun update(updateList: List<OrderItem>) {
        innerItemList.clear()
        innerItemList.addAll(updateList)
        this.notifyDataSetChanged()
    }

}