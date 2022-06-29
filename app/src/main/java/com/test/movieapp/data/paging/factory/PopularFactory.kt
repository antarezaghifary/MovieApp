package com.test.movieapp.data.paging.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.test.movieapp.data.model.popular.ResultsItem
import com.test.movieapp.data.paging.source.PopularSource
import com.test.movieapp.util.VmData

class PopularFactory(
    private val popular: MutableLiveData<VmData<List<ResultsItem>>>,
    private val with_genres: Int,
) : DataSource.Factory<Int, ResultsItem>() {
    override fun create(): DataSource<Int, ResultsItem> {
        return PopularSource(popular, with_genres)
    }
}