package com.example.shipmentsystem.item

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shipmentsystem.databinding.ItemViewBinding

class RvItemAdapter : RecyclerView.Adapter<RvItemAdapter.MyHolder>() {
    private val innerItemList = mutableListOf<Item>()
    private val viewList = mutableListOf<MyHolder>()
    var itemClickListener: (Item) -> Unit = {}

    inner class MyHolder(binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.tvItemName
        var price = binding.tvItemPrice
        var id = binding.tvItemId
        var clickedPosition = -1

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            ItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Log.i("onBindViewHolder", "holder: $holder, pos: $position")

        val currentItem = innerItemList[position]
        viewList.add(holder)
        holder.id.text = currentItem.id.toString()
        holder.name.text = currentItem.name
        holder.price.text = "$ " + currentItem.price.toString()

        holder.itemView.setOnClickListener {
            itemClickListener.invoke(currentItem)

            for(i in 0 until viewList.size){
                println("i: $i")
                viewList[i].itemView.setBackgroundColor(Color.parseColor("#FFAB91"))
                viewList[i].clickedPosition = -1
            }
            holder.clickedPosition = position
            holder.itemView.setBackgroundColor(Color.parseColor("#F57C00"))
        }
        if(holder.clickedPosition == position)
            holder.itemView.setBackgroundColor(Color.parseColor("#F57C00"))
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#FFAB91"))

//        if (holder.rowIndex!=position) {
//            holder.itemView.setBackgroundColor(Color.parseColor("#FFAB91"))
//        } else {
//            holder.itemView.setBackgroundColor(Color.parseColor("#F5F5DC"))
//        }
    }


    override fun getItemCount(): Int = innerItemList.size

    fun update(updateList: List<Item>) {
        innerItemList.clear()
        innerItemList.addAll(updateList)
        this.notifyDataSetChanged()
    }
}