package com.rkddlsgur983.kakaopay.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.rkddlsgur983.kakaopay.BR
import com.rkddlsgur983.kakaopay.R
import com.rkddlsgur983.kakaopay.databinding.ActivityDetailBinding
import com.rkddlsgur983.kakaopay.model.Document
import android.content.Intent
import android.net.Uri


class DetailActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "DETAIL_ACTIVITY"
    }

    private lateinit var binding: ActivityDetailBinding

    private lateinit var document: Document

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        document = intent.getSerializableExtra("DOCUMENT_DATA") as Document

        bind(document)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.menu_detail_open -> {
                openUrl(document.docUrl)
            }
            R.id.menu_detail_save -> {

            }
            else -> {
                // do nothing
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun bind(data: Document) {
        binding.setVariable(BR.document, data)
        binding.executePendingBindings()
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = Uri.parse(url)
        intent.data = uri
        startActivity(intent)
    }
}
