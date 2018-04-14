package com.fed.omdbmemorizer.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fed.omdbmemorizer.R
import kotlinx.android.synthetic.main.list_item.view.title_text_view


class RecyclerAdapter(private var context: Context?,
                      private var items: List<String>) : RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>() {
    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return RecyclerHolder(v)
    }

    override fun getItemCount(): Int = items.size

    inner class RecyclerHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: String) {
            itemView.title_text_view.text = item
        }
    }
}