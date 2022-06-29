package com.test.movieapp.data.paging.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.oratakashi.viewbinding.core.tools.retrofit.transformer.composeSingle
import com.test.movieapp.data.model.popular.ResultsItem
import com.test.movieapp.data.repository.UserRepository
import com.test.movieapp.util.VmData
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException

class PopularSource(
    private val popular: MutableLiveData<VmData<List<ResultsItem>>>,
    private val with_genres: Int
) : PageKeyedDataSource<Int, ResultsItem>(), KoinComponent {

    private val repository: UserRepository by inject()

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultsItem>
    ) {

        popular.postValue(VmData.loading())
        repository.popular(1, with_genres).compose(composeSingle())
            .subscribe({
                if (it.isNotEmpty()) {
                    popular.postValue(VmData.success(it))
                    callback.onResult(it, 1, 2)
                } else popular.postValue(VmData.empty())
            }, {
                if (it is HttpException) {
                    it.response()?.errorBody()?.string()?.let { response ->
                        val message = JSONObject(response).getString("message")
                        popular.value = VmData.fail(it, message)
                    }
                } else {
                    popular.value = VmData.fail(it, it.message)
                }
            }).let { return@let compositeDisposable::add }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ResultsItem>
    ) {
        //authentification
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ResultsItem>
    ) {
        repository.popular(params.key, with_genres).compose(composeSingle())
            .subscribe({
                callback.onResult(it, params.key + 1)
            }, {
                it.printStackTrace()
            }).let { return@let compositeDisposable::add }
    }
}