package com.rkddlsgur983.kakaopay.view

import android.os.Bundle
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
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.view.scrollChangeEvents
import com.jakewharton.rxbinding2.widget.editorActions
import com.jakewharton.rxbinding2.widget.textChanges
import com.rkddlsgur983.kakaopay.R
import com.rkddlsgur983.kakaopay.api.APIConst
import com.rkddlsgur983.kakaopay.databinding.ActivityMainBinding
import com.rkddlsgur983.kakaopay.model.SearchImage
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
                Toast.makeText(applicationContext, R.string.menu_sort_accuracy, Toast.LENGTH_SHORT).show()
            }
            R.id.menu_sort_recency -> {
                sort = APIConst.SORT_RECENCY
                initData()
                setData()
                Toast.makeText(applicationContext, R.string.menu_sort_recency, Toast.LENGTH_SHORT).show()
            }
            R.id.menu_init -> {
                sort = APIConst.SORT_ACCURACY
                initData()
                clearText()
                Toast.makeText(applicationContext, R.string.menu_init, Toast.LENGTH_SHORT).show()
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
        documentAdapter = DocumentAdapter(ArrayList())

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
            if (it != null) {
                searchImage = it
                documentAdapter.addAll(it.documents)
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
        startLoading()
        viewModel.findImages(binding.edSearch.text.toString(), sort, page)
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
}
