package com.test.movieapp.ui.activity.home

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.oratakashi.viewbinding.core.binding.livedata.liveData
import com.test.movieapp.data.model.movie.ResultsItem
import com.test.movieapp.data.paging.factory.MovieFactory
import com.test.movieapp.util.VmData

class MovieViewModel : ViewModel() {

    val popular: MutableLiveData<VmData<List<ResultsItem>>> by liveData(VmData.default())
    val data: MutableLiveData<PagedList<ResultsItem>> by liveData()

    fun initPaging(
        lifecycleOwner: LifecycleOwner,
        with_genres: Int
    ) {
        LivePagedListBuilder(
            MovieFactory(popular, with_genres),
            10
        ).build().observe(lifecycleOwner, data::postValue)
    }

}