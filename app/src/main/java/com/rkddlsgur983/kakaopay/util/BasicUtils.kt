package com.rkddlsgur983.kakaopay.util

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class BasicUtils {

    companion object {

        fun showToast(context: Context, msg: Int) {
            Toast.makeText(context, context.getString(msg), Toast.LENGTH_SHORT).show()
        }

        fun getCurrentDate(): String {

            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.KOREA)
            val date = Date()
            return dateFormat.format(date)
        }
    }
}