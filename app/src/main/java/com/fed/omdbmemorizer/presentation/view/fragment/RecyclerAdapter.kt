package com.fed.omdbmemorizer.presentation.view.fragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fed.omdbmemorizer.R
import com.fed.omdbmemorizer.presentation.model.MovieModel
import kotlinx.android.synthetic.main.list_item.view.date_text_view
import kotlinx.android.synthetic.main.list_item.view.favorite_text_view
import kotlinx.android.synthetic.main.list_item.view.preview_image_view
import kotlinx.android.synthetic.main.list_item.view.title_text_view


class RecyclerAdapter(private var context: Context?,
                      private var movies: ArrayList<MovieModel>,
                      private val isSearch: Boolean,
                      private val favoriteClickListener: (MovieModel) -> Unit
                      )
: RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>() {
    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return RecyclerHolder(v)
    }

    fun addMovies(movies: List<MovieModel>) {
        this.movies.addAll(movies)
    }

    fun setMovies(movies: List<MovieModel>) {
        this.movies = movies as ArrayList<MovieModel>
    }

    fun clearMovies() {
        movies.clear()
    }

    override fun getItemCount(): Int = movies.size

    inner class RecyclerHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: MovieModel) {
            itemView.title_text_view.text = item.title
            if (item.poster.isNotEmpty() && item.poster != "N/A") {
            Glide.with(context!!)
                    .load(item.poster)
                    .apply(RequestOptions()
                            .placeholder(R.drawable.android))
                    .into(itemView.preview_image_view)
                itemView.preview_image_view.visibility = View.VISIBLE
            } else {
                itemView.preview_image_view.visibility = View.GONE
            }
            itemView.date_text_view.text = item.year

            val res = context?.resources
            itemView.favorite_text_view.text =
                    if (isSearch) res?.getString(R.string.add_favorite_text)
                    else res?.getString(R.string.remove_favorite_text)
            itemView.favorite_text_view.setOnClickListener { favoriteClickListener.invoke(item) }
        }
    }
}