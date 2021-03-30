package com.example.shipmentsystem.ship.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shipmentsystem.databinding.ItemEditBinding
import com.example.shipmentsystem.db.OrderItem

class RvEditAdapter : RecyclerView.Adapter<RvEditAdapter.MyHolder>() {
    private val innerList = mutableListOf<OrderItem>()
    var itemClickListener: (OrderItem, Int) -> Unit = { orderItem: OrderItem, int: Int -> }
    var changeBackgroundListener: (OrderItem, MyHolder) -> Unit =
        { orderItem: OrderItem, myHolder: MyHolder -> }

    inner class MyHolder(binding: ItemEditBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.tvItemName
        var price = binding.tvItemPrice
        var id = binding.tvItemId
        val amount = binding.tvAmount
        private val check = binding.checkBox


        fun bind(currentItem: OrderItem, holder: MyHolder) {
            holder.id.text = (adapterPosition + 1).toString()
            holder.name.text = currentItem.name
            holder.price.text = currentItem.sumPrice.toString()
            holder.amount.text = currentItem.amount.toString()

            changeBackgroundListener.invoke(currentItem, holder)

            holder.itemView.setOnClickListener {
                itemClickListener.invoke(innerList[adapterPosition], adapterPosition)
            }
            holder.check.setOnCheckedChangeListener { compoundButton, isCheck ->
                currentItem.isChecked = isCheck
            }
            holder.check.isChecked = currentItem.isChecked

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val myHolder = MyHolder(
            ItemEditBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

        return myHolder
    }


    override fun getItemCount() = innerList.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentItem = innerList[position]
        holder.bind(currentItem, holder)
    }

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