package com.rkddlsgur983.kakaopay.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.rkddlsgur983.kakaopay.BR
import com.rkddlsgur983.kakaopay.databinding.ActivityDetailBinding
import com.rkddlsgur983.kakaopay.model.Document
import android.content.Intent
import android.net.Uri
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.view.longClicks
import com.rkddlsgur983.kakaopay.R
import com.rkddlsgur983.kakaopay.viewmodel.DetailViewModel


class DetailActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "DETAIL_ACTIVITY"
    }

    private lateinit var binding: ActivityDetailBinding
    private val viewModel = DetailViewModel()

    private var title = ""
    private lateinit var document: Document

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        initView()
        initObservable()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }

            R.id.menu_detail_open -> {
                openUrl(document.docUrl)
            }
            R.id.menu_detail_save -> {
                viewModel.saveImage(document)
            }
            else -> {
                // do nothing
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getData() {
        title = intent.getStringExtra("TEXT_DATA")
        document = intent.getSerializableExtra("DOCUMENT_DATA") as Document
    }

    private fun bind(title: String, document: Document) {
        binding.setVariable(BR.title, title)
        binding.setVariable(BR.document, document)
        binding.executePendingBindings()
    }

    private fun initView() {

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        getData()
        bind(title, document)
    }

    private fun initObservable() {

        viewModel.bind(
            binding.ivImage.clicks().subscribe {
                openUrl(document.docUrl)
            },
            binding.ivImage.longClicks().subscribe {
                viewModel.saveImage(document)
            }
        )
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = Uri.parse(url)
        intent.data = uri
        startActivity(intent)
    }
}
