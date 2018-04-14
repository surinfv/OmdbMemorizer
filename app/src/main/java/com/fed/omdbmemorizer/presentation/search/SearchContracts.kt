package com.fed.omdbmemorizer.presentation.search

import com.fed.omdbmemorizer.model.MovieDTO


interface SearchContracts {
    interface Fragment {
        fun updateData(movies: ArrayList<MovieDTO>)
        fun showToast(message: String)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun onAttach(fragment: SearchContracts.Fragment)
        fun onDetach()
        fun searchTextEntered(title: String)
        fun clearButtonClicked()
    }

}