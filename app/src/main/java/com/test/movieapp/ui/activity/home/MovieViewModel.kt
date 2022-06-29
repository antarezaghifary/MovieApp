package com.test.movieapp.ui.activity.home

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.oratakashi.viewbinding.core.binding.livedata.liveData
import com.test.movieapp.data.model.popular.ResultsItem
import com.test.movieapp.data.paging.factory.PopularFactory
import com.test.movieapp.util.VmData
import org.koin.core.component.KoinComponent

class MovieViewModel : ViewModel(), KoinComponent {

    val popular: MutableLiveData<VmData<List<ResultsItem>>> by liveData(VmData.default())
    val data: MutableLiveData<PagedList<ResultsItem>> by liveData()

    fun initPaging(
        lifecycleOwner: LifecycleOwner,
        with_genres: Int
    ) {
        LivePagedListBuilder(
            PopularFactory(popular, with_genres),
            10
        ).build().observe(lifecycleOwner, data::postValue)
    }

}