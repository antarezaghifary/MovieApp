package com.test.movieapp.ui.activity.home

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.oratakashi.viewbinding.core.binding.livedata.liveData
import com.test.movieapp.data.model.movie.ResultsItem
import com.test.movieapp.data.paging.factory.MovieFactory
import com.test.movieapp.util.VmData
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent

class MovieViewModel : ViewModel(), KoinComponent {

    val popular: MutableLiveData<VmData<List<ResultsItem>>> by liveData(VmData.default())
    val data: MutableLiveData<PagedList<ResultsItem>> by liveData()

    fun initPaging(
        lifecycleOwner: LifecycleOwner,
        with_genres: Int
    ) {
        Pager(
            PagingConfig(
                10
            ),
            this.initialLoadKey,
            MovieFactory(popular, with_genres).asPagingSourceFactory(Dispatchers.IO)
        ).liveData.build().observe(lifecycleOwner, data::postValue)
    }

}