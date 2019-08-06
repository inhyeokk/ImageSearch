package com.rkddlsgur983.kakaopay.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rkddlsgur983.kakaopay.model.Document
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DetailViewModel: ViewModel() {

    val saveImageLiveData = MutableLiveData<String>()
    private val compositeDisposable = CompositeDisposable()

    fun bind(vararg disposables: Disposable) {
        compositeDisposable.addAll(*disposables)
    }

    fun saveImage(document: Document) {

    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}