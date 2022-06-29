package com.test.movieapp.data.paging.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.test.movieapp.data.paging.source.ReviewSource
import com.test.movieapp.util.VmData

class ReviewFactory(
    private val review: MutableLiveData<VmData<List<com.test.movieapp.data.model.review.ResultsItem>>>,
    private val movie_id: Int,
) : DataSource.Factory<Int, com.test.movieapp.data.model.review.ResultsItem>() {
    override fun create(): DataSource<Int, com.test.movieapp.data.model.review.ResultsItem> {
        return ReviewSource(review, movie_id)
    }
}