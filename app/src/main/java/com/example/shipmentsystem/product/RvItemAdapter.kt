package com.example.shipmentsystem.product

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shipmentsystem.databinding.ItemViewBinding
import com.example.shipmentsystem.db.Product

class RvItemAdapter : RecyclerView.Adapter<RvItemAdapter.MyHolder>() {
    private val innerItemList = mutableListOf<Product>()
    var itemClickListener: (Product) -> Unit = {}
    var changeBackgroundListener: (Product, MyHolder) -> Unit = { product: Product, myHolder: MyHolder -> }

    inner class MyHolder(binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.tvItemName
        var price = binding.tvItemPrice
        var id = binding.tvItemId

        fun bind(product: Product, holder: MyHolder) {
            changeBackgroundListener.invoke(product, holder)

            holder.itemView.setOnClickListener {
                itemClickListener.invoke(innerItemList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val myHolder = MyHolder(
            ItemViewBinding.inflate(
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
        holder.price.text = "$ ${currentItem.price}"
        holder.bind(currentItem, holder)
    }

    override fun getItemCount(): Int = innerItemList.size

    fun update(updateList: List<Product>) {
        innerItemList.clear()
        innerItemList.addAll(updateList)
        this.notifyDataSetChanged()
    }
}