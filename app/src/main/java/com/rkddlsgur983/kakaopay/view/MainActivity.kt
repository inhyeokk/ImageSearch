package com.rkddlsgur983.kakaopay.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jakewharton.rxbinding2.support.v4.widget.refreshes
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerViewAdapter
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.view.scrollChangeEvents
import com.jakewharton.rxbinding2.widget.editorActions
import com.jakewharton.rxbinding2.widget.textChanges
import com.rkddlsgur983.kakaopay.R
import com.rkddlsgur983.kakaopay.api.APIConst
import com.rkddlsgur983.kakaopay.databinding.ActivityMainBinding
import com.rkddlsgur983.kakaopay.model.SearchImage
import com.rkddlsgur983.kakaopay.util.BasicUtils
import com.rkddlsgur983.kakaopay.view.listener.DocumentItemListener
import com.rkddlsgur983.kakaopay.viewmodel.MainViewModel
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    private lateinit var documentAdapter: DocumentAdapter
    private lateinit var linearLayoutManager: StaggeredGridLayoutManager

    private var searchImage: SearchImage? = null
    private var sort = APIConst.SORT_ACCURACY
    private var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initView()
        initObservable()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sort, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.menu_sort_accuracy -> {
                sort = APIConst.SORT_ACCURACY
                initData()
                setData()
                BasicUtils.showToast(applicationContext, R.string.menu_sort_accuracy)
            }
            R.id.menu_sort_recency -> {
                sort = APIConst.SORT_RECENCY
                initData()
                setData()
                BasicUtils.showToast(applicationContext, R.string.menu_sort_recency)
            }
            R.id.menu_init -> {
                sort = APIConst.SORT_ACCURACY
                initData()
                clearText()
                BasicUtils.showToast(applicationContext, R.string.menu_init)
            }
            else -> {
                // do nothing
            }
        }
        return super.onOptionsItemSelected(item)
    }
    
    private fun initView() {

        linearLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        linearLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        documentAdapter = DocumentAdapter(ArrayList(), documentItemListener)

        binding.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = documentAdapter
        }
    }

    private fun initObservable() {

        viewModel.bind(
            binding.btnSearch.clicks().subscribe {
                initData()
                setData()
            },
            binding.btnCancel.clicks().subscribe {
                clearText()
            },
            binding.edSearch.editorActions().subscribe {
                actionId -> when(actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        initData()
                        setData()
                    } else -> {
                        // do nothing
                    }
                }
            },
            binding.edSearch.textChanges().subscribe {
                if (it.isNotEmpty()) {
                    binding.btnCancel.apply {
                        isClickable = true
                        isFocusable = true
                        visibility = View.VISIBLE
                    }
                } else {
                    binding.btnCancel.apply {
                        isClickable = false
                        isFocusable = false
                        visibility = View.GONE
                    }
                }
            },
            binding.recyclerView.scrollChangeEvents().subscribe {
                if (!binding.recyclerView.canScrollVertically(1)) {
                    searchImage?.let {
                        if (!it.meta.isEnd) {
                            viewModel.findImages(binding.edSearch.text.toString(), sort, ++page)
                        } else {
                            BasicUtils.showToast(applicationContext, R.string.toast_common_last_page)
                        }
                    }
                }
            },
            binding.refreshLayout.refreshes().subscribe {
                initData()
                setData()
            }
        )

        viewModel.searchImageLiveData.observe(this, Observer {
            searchImage = it
            if (it != null) {
                documentAdapter.addAll(it.documents)
                if (it.documents.isEmpty()) {
                    BasicUtils.showToast(applicationContext, R.string.main_result_none)
                }
            } else {
                BasicUtils.showToast(applicationContext, R.string.toast_common_network_fail)
            }
            stopLoading()
        })
    }

    private fun initData() {
        binding.recyclerView.recycledViewPool.clear()
        documentAdapter.clear()
        page = 1
    }

    private fun setData() {
        if (binding.edSearch.text.isNotEmpty()) {
            startLoading()
            viewModel.findImages(binding.edSearch.text.toString(), sort, page)
        } else {
            BasicUtils.showToast(applicationContext, R.string.toast_common_text_none)
        }
    }

    private fun clearText() {
        binding.edSearch.text.clear()
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

    private val documentItemListener = object: DocumentItemListener {

        override fun onDocumentClick(view: View, position: Int) {

            val intent = Intent(applicationContext, DetailActivity::class.java)
            intent.putExtra("DOCUMENT_DATA", documentAdapter.getItem()[position])
            startActivity(intent)
        }
    }
}
