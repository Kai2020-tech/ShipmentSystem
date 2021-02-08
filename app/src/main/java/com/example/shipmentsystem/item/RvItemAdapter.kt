package com.example.shipmentsystem.item

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shipmentsystem.databinding.ItemViewBinding

class RvItemAdapter : RecyclerView.Adapter<RvItemAdapter.MyHolder>() {
    private val innerItemList = mutableListOf<Item>()
    private val viewList = mutableListOf<MyHolder>()
    var itemClickListener: (Item) -> Unit = {}
    private val itemSelectedColor = "#F57C00"
    private val itemDefaultColor = "#FFAB91"

    inner class MyHolder(binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.tvItemName
        var price = binding.tvItemPrice
        var id = binding.tvItemId
        var clickedPosition = RecyclerView.NO_POSITION

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
            if (myHolder.clickedPosition != myHolder.adapterPosition) {
                //when item clicked, change background color
                myHolder.itemView.setBackgroundColor(Color.parseColor(itemSelectedColor))
                myHolder.clickedPosition = myHolder.adapterPosition
            }else{
                //the same item clicked again, set background color to default
                myHolder.itemView.setBackgroundColor(Color.parseColor(itemDefaultColor))
                myHolder.clickedPosition = RecyclerView.NO_POSITION
            }
        }
        return myHolder
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentItem = innerItemList[position]
        holder.id.text = currentItem.id.toString()
        holder.name.text = currentItem.name
        holder.price.text = "$ " + currentItem.price.toString()

//        holder.itemView.setOnClickListener {
//            itemClickListener.invoke(currentItem)
//            if (holder.clickedPosition != position) {
//                //when item clicked, change background color
//                holder.itemView.setBackgroundColor(Color.parseColor(itemSelectedColor))
//                holder.clickedPosition = position
//            }else{
//                //the same item clicked again, set background color to default
//                holder.itemView.setBackgroundColor(Color.parseColor(itemDefaultColor))
//                holder.clickedPosition = RecyclerView.NO_POSITION
//            }
//        }
        if (holder.clickedPosition == position)
            holder.itemView.setBackgroundColor(Color.parseColor(itemSelectedColor))
        else
            holder.itemView.setBackgroundColor(Color.parseColor(itemDefaultColor))

    }


    override fun getItemCount(): Int = innerItemList.size

    fun update(updateList: List<Item>) {
        innerItemList.clear()
        innerItemList.addAll(updateList)
        this.notifyDataSetChanged()
    }
}