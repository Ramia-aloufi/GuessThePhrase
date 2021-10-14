package com.example.guessthephrase

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.textcell.view.*

class MyAdapter(var item:ArrayList<Items>):RecyclerView.Adapter<MyAdapter.ItemViewHolder>() {
    class ItemViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.textcell,parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var items = item[position]
        holder.itemView.apply {if(items.color == 0){
            tv.setTextColor(Color.GREEN)
         }else if(items.color == 1){
            tv.setTextColor(Color.RED)
        }else{
            tv.setTextColor(Color.BLACK)
        }
            tv.text = items.textval
    }}

    override fun getItemCount(): Int = item.size
}