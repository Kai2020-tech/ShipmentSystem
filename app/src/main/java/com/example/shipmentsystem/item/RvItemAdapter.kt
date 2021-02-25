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
    private val viewList = mutableListOf<MyHolder>()
    var itemClickListener: (Item) -> Unit = {}
    lateinit var itemVM: ItemViewModel
    lateinit var lifecycleOwner: LifecycleOwner
    private val selectedColor = "#F57C00"
    private val unSelectedColor = "#FFAB91"

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
//            if (myHolder.clickedPosition != myHolder.adapterPosition) {
//                //when item clicked, change background color
//                myHolder.itemView.setBackgroundColor(Color.parseColor(selectedColor))
//                myHolder.clickedPosition = myHolder.adapterPosition
//            } else {
//                //the same item clicked again, set background color to default
//                myHolder.itemView.setBackgroundColor(Color.parseColor(unSelectedColor))
//                myHolder.clickedPosition = RecyclerView.NO_POSITION
//            }
//            if (itemVM.selectedItem.value != null) {
//                myHolder.itemView.setBackgroundColor(Color.parseColor(selectedColor))
//                myHolder.clickedPosition = myHolder.adapterPosition
//            } else {
//                myHolder.itemView.setBackgroundColor(Color.parseColor(unSelectedColor))
//                myHolder.clickedPosition = RecyclerView.NO_POSITION
//            }
        }
        return myHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentItem = innerItemList[position]
        holder.id.text = currentItem.id.toString()
        holder.name.text = currentItem.name
        holder.price.text = "$ ${currentItem.price}"

//        if (holder.clickedPosition == position)
//            holder.itemView.setBackgroundColor(Color.parseColor(selectedColor))
//        else
//            holder.itemView.setBackgroundColor(Color.parseColor(unSelectedColor))

        itemVM.selectedItem.observe(lifecycleOwner, Observer {
            if(currentItem.id == itemVM.selectedItem.value?.id){
                holder.itemView.setBackgroundColor(Color.parseColor(selectedColor))
            }else{
                holder.itemView.setBackgroundColor(Color.parseColor(unSelectedColor))
            }
        })


    }


    override fun getItemCount(): Int = innerItemList.size

    fun update(updateList: List<Item>) {
        innerItemList.clear()
        innerItemList.addAll(updateList)
        this.notifyDataSetChanged()
    }

    fun getItemVM(vm: ItemViewModel) {
        itemVM = vm
    }

    fun getViewLifecycleOwner(owner: LifecycleOwner){
        lifecycleOwner = owner
    }
}