package com.test.movieapp.ui.activity.genre

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.oratakashi.viewbinding.core.binding.livedata.liveData
import com.test.movieapp.data.model.popular.GenresItem
import com.test.movieapp.data.model.popular.ResultsItem
import com.test.movieapp.data.repository.UserRepository
import com.test.movieapp.util.VmData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException

class GenreViewModel : ViewModel(), KoinComponent {

    val genre: MutableLiveData<VmData<List<GenresItem>>> by liveData(VmData.default())
    val data: MutableLiveData<PagedList<ResultsItem>> by liveData()
    private val repository: UserRepository by inject()
    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    fun genres() {
        genre.value = VmData.loading()
        repository.genre()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                genre.value = VmData.success(it)
            }, {
                if (it is HttpException) {
                    it.response()?.errorBody()?.string()?.let { response ->
                        val message = JSONObject(response).getString("message")
                        genre.value = VmData.fail(it, message)
                    }
                } else {
                    genre.value = VmData.fail(it, it.message)
                }
            }).let { return@let compositeDisposable::add }
    }

}