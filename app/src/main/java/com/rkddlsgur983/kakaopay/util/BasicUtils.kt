package com.rkddlsgur983.kakaopay.util

import android.content.Context
import android.widget.Toast

class BasicUtils {

    companion object {

        fun showToast(context: Context, msg: Int) {
            Toast.makeText(context, context.getString(msg), Toast.LENGTH_SHORT).show()
        }
    }
}