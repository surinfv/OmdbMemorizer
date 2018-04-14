package com.fed.omdbmemorizer.ui.search

import com.fed.omdbmemorizer.model.MovieDTO


interface SearchContracts {
    interface Fragment {
        fun updateData(movies: List<MovieDTO>)
        fun showToast(message: String)
    }

    interface Presenter {
        fun onAttach(fragment: SearchContracts.Fragment)
        fun onDetach()
        fun doRequest(title: String)
    }

}