package com.test.movieapp.ui.activity.detail

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.oratakashi.viewbinding.core.binding.livedata.liveData
import com.test.movieapp.data.paging.factory.ReviewFactory
import com.test.movieapp.util.VmData

class DetailMovieViewModel : ViewModel() {

    val review: MutableLiveData<VmData<List<com.test.movieapp.data.model.review.ResultsItem>>> by liveData(
        VmData.default()
    )
    val data: MutableLiveData<PagedList<com.test.movieapp.data.model.review.ResultsItem>> by liveData()

    fun initPaging(
        lifecycleOwner: LifecycleOwner,
        movie_id: Int
    ) {
        LivePagedListBuilder(
            ReviewFactory(review, movie_id),
            3
        ).build().observe(lifecycleOwner, data::postValue)
    }
}