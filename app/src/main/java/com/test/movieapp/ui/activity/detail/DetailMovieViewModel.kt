package com.test.movieapp.ui.activity.detail

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.oratakashi.viewbinding.core.binding.livedata.liveData
import com.test.movieapp.data.model.video.ThumbnailResponse
import com.test.movieapp.data.paging.factory.ReviewFactory
import com.test.movieapp.data.repository.UserRepository
import com.test.movieapp.util.VmData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException

class DetailMovieViewModel : ViewModel(), KoinComponent {

    val review: MutableLiveData<VmData<List<com.test.movieapp.data.model.review.ResultsItem>>> by liveData(
        VmData.default()
    )

    val trailer: MutableLiveData<VmData<List<com.test.movieapp.data.model.trailer.ResultsItem>>> by liveData(
        VmData.default()
    )

    val thumbnail: MutableLiveData<VmData<ThumbnailResponse>> by liveData(
        VmData.default()
    )

    private val repository: UserRepository by inject()
    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

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


    fun trailer(movie_id: Int) {
        trailer.value = VmData.loading()
        repository.trailer(movie_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                trailer.value = VmData.success(it)
            }, {
                if (it is HttpException) {
                    it.response()?.errorBody()?.string()?.let { response ->
                        val message = JSONObject(response).getString("message")
                        trailer.value = VmData.fail(it, message)
                    }
                } else {
                    trailer.value = VmData.fail(it, it.message)
                }
            }).let { return@let compositeDisposable::add }
    }
}