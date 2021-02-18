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
    var i = 0

    inner class MyHolder(binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.tvItemName
        var price = binding.tvItemPrice
        var id = binding.tvItemId
        var clickedPosition = -1

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        i++
        return MyHolder(
            ItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentItem = innerItemList[position]
        viewList.add(holder)
        holder.id.text = currentItem.id.toString()
        holder.name.text = currentItem.name
        holder.price.text = "$ " + currentItem.price.toString()

        holder.itemView.setOnClickListener {
            itemClickListener.invoke(currentItem)

            for (i in 0 until viewList.size) {
                viewList[i]?.itemView?.setBackgroundColor(Color.parseColor("#FFAB91"))
                viewList[i].clickedPosition = -1
            }
//            this.notifyDataSetChanged()
            holder.clickedPosition = position
            holder.itemView.setBackgroundColor(Color.parseColor("#F57C00"))
        }
        if (holder.clickedPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#F57C00"))
            Log.d("viewHolder","clicked ${holder.clickedPosition}, adapterPosition ${holder.adapterPosition}" +
                    "holder $holder ")
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFAB91"))
            Log.d("viewHolder","adapterPosition ${holder.adapterPosition} ")
            Log.d("viewHolder","clicked ${holder.clickedPosition}")
        }


        Log.d("viewHolder counts", "$i")
        Log.d("viewHolder", "$holder")  //看有多少個viewHolder

    }

    override fun getItemCount(): Int = innerItemList.size

    fun update(updateList: List<Item>) {
        innerItemList.clear()
        innerItemList.addAll(updateList)
        this.notifyDataSetChanged()
    }
}