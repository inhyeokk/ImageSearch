package com.rkddlsgur983.kakaopay.api

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class APIUtils {

    companion object {

        fun findImages(query: String) {

            val retrofitService: APIInterface
                    = APIConnectionManager.getRetrofitService(APIInterface::class.java)

            val disposable = retrofitService.findImages(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d("connection success", it.toString())
                }, {
                    Log.e("connection fail", it.message)
                })
        }
    }
}