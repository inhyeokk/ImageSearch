package com.rkddlsgur983.kakaopay.view.detail

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DetailViewModel: ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun bind(vararg disposables: Disposable) {
        compositeDisposable.addAll(*disposables)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}