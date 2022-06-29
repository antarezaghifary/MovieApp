package com.test.movieapp.data.paging.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.oratakashi.viewbinding.core.tools.retrofit.transformer.composeSingle
import com.test.movieapp.data.repository.UserRepository
import com.test.movieapp.util.VmData
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException

class ReviewSource(
    private val review: MutableLiveData<VmData<List<com.test.movieapp.data.model.review.ResultsItem>>>,
    private val movie_id: Int
) : PageKeyedDataSource<Int, com.test.movieapp.data.model.review.ResultsItem>(), KoinComponent {

    private val repository: UserRepository by inject()

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, com.test.movieapp.data.model.review.ResultsItem>
    ) {

        review.postValue(VmData.loading())
        repository.review(1, movie_id).compose(composeSingle())
            .subscribe({
                if (it.isNotEmpty()) {
                    review.postValue(VmData.success(it))
                    callback.onResult(it, 1, 2)
                } else review.postValue(VmData.empty())
            }, {
                if (it is HttpException) {
                    it.response()?.errorBody()?.string()?.let { response ->
                        val message = JSONObject(response).getString("message")
                        review.value = VmData.fail(it, message)
                    }
                } else {
                    review.value = VmData.fail(it, it.message)
                }
            }).let { return@let compositeDisposable::add }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, com.test.movieapp.data.model.review.ResultsItem>
    ) {
        //authentification
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, com.test.movieapp.data.model.review.ResultsItem>
    ) {
        repository.review(params.key, movie_id).compose(composeSingle())
            .subscribe({
                callback.onResult(it, params.key + 1)
            }, {
                it.printStackTrace()
            }).let { return@let compositeDisposable::add }
    }

}