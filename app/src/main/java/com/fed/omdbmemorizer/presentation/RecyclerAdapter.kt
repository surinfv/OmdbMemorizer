package com.fed.omdbmemorizer.presentation

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.fed.omdbmemorizer.R
import com.fed.omdbmemorizer.model.MovieDTO
import kotlinx.android.synthetic.main.list_item.view.date_text_view
import kotlinx.android.synthetic.main.list_item.view.preview_image_view
import kotlinx.android.synthetic.main.list_item.view.title_text_view


class RecyclerAdapter(private var context: Context?,
                      private var movies: List<MovieDTO>) : RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>() {
    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return RecyclerHolder(v)
    }

    fun setMovies(movies: ArrayList<MovieDTO>) {
        this.movies = movies
    }

    override fun getItemCount(): Int = movies.size

    inner class RecyclerHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: MovieDTO) {
            itemView.title_text_view.text = item.title
            if (item.poster.isNotEmpty()) {
            Glide.with(context!!)
                    .load(item.poster)
//                        .placeholder(R.drawable.piwo_48)
                    .into(itemView.preview_image_view)
                itemView.preview_image_view.visibility = View.VISIBLE
            } else {
                itemView.preview_image_view.visibility = View.GONE
            }
            itemView.date_text_view.text = item.year
        }
    }
}