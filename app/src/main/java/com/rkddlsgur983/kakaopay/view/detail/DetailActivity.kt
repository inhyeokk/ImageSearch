package com.rkddlsgur983.kakaopay.view.detail

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.rkddlsgur983.kakaopay.BR
import com.rkddlsgur983.kakaopay.databinding.ActivityDetailBinding
import com.rkddlsgur983.kakaopay.model.Document
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.view.longClicks
import com.rkddlsgur983.kakaopay.R
import com.rkddlsgur983.kakaopay.util.BasicUtils
import java.io.File

class DetailActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "DETAIL_ACTIVITY"
        const val REQUEST_WRITE_EXTERNAL_STORAGE = 1111
    }

    private lateinit var binding: ActivityDetailBinding
    private val viewModel = DetailViewModel()
    private lateinit var downloadManager: DownloadManager

    private var title = ""
    private lateinit var document: Document

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

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
                getDialog()
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
                getDialog()
            }
        )
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = Uri.parse(url)
        intent.data = uri
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {
            REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveImage(downloadManager, document)
                }
            }
            else -> {
                // do nothing
            }
        }
    }

    private fun getDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle(getString(R.string.download_dialog_title))
            setMessage(getString(R.string.download_dialog_msg, title))
            setPositiveButton(getString(R.string.download_dialog_positive), {
                _, _ -> getPermission()
            })
            setNegativeButton(getString(R.string.download_dialog_negative), {
                _, _ -> // do nothing
            })
            show()
        }
    }

    private fun getPermission() {

        val permissionCheck = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_EXTERNAL_STORAGE)
        } else {
            // permission granted
            saveImage(downloadManager, document)
        }
    }

    private fun saveImage(downloadManager: DownloadManager, document: Document) {
        val SAVE_FOLDER = getString(R.string.download_save_path)
        val savePath = Environment.getExternalStorageDirectory().toString() + SAVE_FOLDER
        val fileName = title + "_" + BasicUtils.getCurrentDate() + ".jpg"
        val file = File(savePath, fileName)

        val request = DownloadManager.Request(Uri.parse(document.imageUrl))
            .setTitle(getString(R.string.download_dialog_title))
            .setDescription(fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationUri(Uri.fromFile(file))
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        downloadManager.enqueue(request)
    }
}
