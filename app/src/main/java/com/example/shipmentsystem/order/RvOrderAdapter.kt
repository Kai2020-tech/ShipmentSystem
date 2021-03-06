package com.example.shipmentsystem.order

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shipmentsystem.databinding.ItemOrderBinding
import com.example.shipmentsystem.db.OrderItem

class RvOrderAdapter : RecyclerView.Adapter<RvOrderAdapter.MyHolder>() {
    private val innerList = mutableListOf<OrderItem>()
    var itemClickListener: (OrderItem, Int) -> Unit = { orderItem: OrderItem, int: Int -> }
    var changeBackgroundListener: (OrderItem, MyHolder) -> Unit =
        { orderItem: OrderItem, myHolder: MyHolder -> }

    inner class MyHolder(binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.tvItemName
        var price = binding.tvItemPrice
        var id = binding.tvItemId
        val amount = binding.tvAmount

        fun bind(orderItem: OrderItem, holder: MyHolder) {
            changeBackgroundListener.invoke(orderItem, holder)

            holder.itemView.setOnClickListener {
                itemClickListener.invoke(innerList[adapterPosition], adapterPosition)
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
        val currentItem = innerList[position]
        holder.id.text = (position + 1).toString()
        holder.name.text = currentItem.name
        holder.price.text = "$ ${currentItem.sumPrice}"
        holder.amount.text = currentItem.amount.toString()
        holder.bind(currentItem, holder)
    }

    override fun getItemCount(): Int = innerList.size

    fun updateList(updateList: List<OrderItem>) {
        innerList.clear()
        innerList.addAll(updateList)
        this.notifyDataSetChanged()
    }

    fun updateItem(int: Int, item: OrderItem) {
        innerList[int] = item
        this.notifyItemChanged(int)
    }

}