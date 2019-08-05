package com.rkddlsgur983.kakaopay.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rkddlsgur983.kakaopay.api.APIConnectionManager
import com.rkddlsgur983.kakaopay.api.APIConst
import com.rkddlsgur983.kakaopay.api.APIInterface
import com.rkddlsgur983.kakaopay.model.SearchImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel: ViewModel() {

    val searchImageLiveData = MutableLiveData<SearchImage>()
    private val compositeDisposable = CompositeDisposable()

    fun bind(vararg disposables: Disposable) {
        compositeDisposable.addAll(*disposables)
    }

    fun findImages(query: String, page: Int) {

        val retrofitService: APIInterface
                = APIConnectionManager.getRetrofitService(APIInterface::class.java)

        retrofitService.findImages(query, APIConst.SORT_ACCURACY, page, APIConst.SIZE_TWENTY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("connection success", it.toString())
                searchImageLiveData.postValue(it)
            }, {
                Log.e("connection fail", it.message)
                searchImageLiveData.postValue(null)
            }).apply {
                compositeDisposable.add(this)
            }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}