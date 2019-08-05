package com.rkddlsgur983.kakaopay.view

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.support.v4.widget.refreshes
import com.jakewharton.rxbinding2.support.v7.widget.scrollEvents
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.view.scrollChangeEvents
import com.jakewharton.rxbinding2.widget.editorActions
import com.rkddlsgur983.kakaopay.R
import com.rkddlsgur983.kakaopay.databinding.ActivityMainBinding
import com.rkddlsgur983.kakaopay.model.Document
import com.rkddlsgur983.kakaopay.viewmodel.MainViewModel
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    private lateinit var documentAdapter: DocumentAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val documents = ArrayList<Document>()

    private var isLast: Boolean = false
    private var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initView()
        initObservable()
    }
    
    private fun initView() {

        linearLayoutManager = GridLayoutManager(applicationContext, 2, RecyclerView.VERTICAL, false)
        documentAdapter = DocumentAdapter(documents)

        binding.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = documentAdapter
        }
    }

    private fun initObservable() {

        viewModel.bind(
            binding.btnSearch.clicks().subscribe {
                initData()
                startLoading()
                viewModel.findImages(binding.edSearch.text.toString(), page)
            },
            binding.edSearch.editorActions().subscribe {
                actionId -> when(actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        initData()
                        startLoading()
                        viewModel.findImages(binding.edSearch.text.toString(), page)
                    } else -> {
                        // do nothing
                    }
                }
            },
            binding.recyclerView.scrollChangeEvents().subscribe {
                if (isLast) {
                    viewModel.findImages(binding.edSearch.text.toString(), ++page)
                }
            },
            binding.recyclerView.scrollEvents().subscribe {
                isLast = documentAdapter.itemCount - 1 <= linearLayoutManager.findLastVisibleItemPosition()
            },
            binding.refreshLayout.refreshes().subscribe {
                initData()
                startLoading()
                viewModel.findImages(binding.edSearch.text.toString(), page)
            }
        )

        viewModel.searchImageLiveData.observe(this, Observer {
            if (it != null) {
                documentAdapter.addAll(it.documents)
            }
            stopLoading()
        })
    }

    private fun initData() {
        documentAdapter.clear()
        isLast = false
        page = 1
    }

    private fun startLoading() {
        if (!binding.refreshLayout.isRefreshing) {
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    private fun stopLoading() {
        if (isProgressLoading()) {
            binding.progressBar.visibility = View.GONE
        } else if (binding.refreshLayout.isRefreshing) {
            binding.refreshLayout.isRefreshing = false
        }
    }

    private fun isProgressLoading(): Boolean {
        return binding.progressBar.visibility == View.VISIBLE
    }
}
