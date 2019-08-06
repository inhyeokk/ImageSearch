package com.rkddlsgur983.kakaopay.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rkddlsgur983.kakaopay.R
import com.rkddlsgur983.kakaopay.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "DETAIL_ACTIVITY"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
    }
}
