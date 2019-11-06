package com.rkddlsgur983.kakaopay.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rkddlsgur983.kakaopay.service.retrofit.RetrofitManager
import com.rkddlsgur983.kakaopay.service.kakao.KakaoConst
import com.rkddlsgur983.kakaopay.service.kakao.KakaoService
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

    fun findImages(query: String, sort: String, page: Int) {

        val kakaoService: KakaoService
                = RetrofitManager.getRetrofitService(
            KakaoService::class.java)

        kakaoService.findImages(query, sort, page, KakaoConst.SIZE_TWENTY)
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