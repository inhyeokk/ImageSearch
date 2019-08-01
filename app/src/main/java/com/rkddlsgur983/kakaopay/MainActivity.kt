package com.rkddlsgur983.kakaopay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rkddlsgur983.kakaopay.api.APIUtils
import com.rkddlsgur983.kakaopay.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.btnSearch.setOnClickListener({
            APIUtils.findImages(binding.edSearch.text.toString())
        })
    }
}
