package com.rkddlsgur983.kakaopay.util

import android.content.Context
import android.widget.TextView
import android.widget.Toast

class BasicUtils {

    companion object {

        fun showToast(context: Context, msg: Int) {
            Toast.makeText(context, context.getString(msg), Toast.LENGTH_SHORT).show()
        }

        fun setTitleUrl(view: TextView, url: String?) {

            view.text = url
        }

        fun setCollection(collection: String?, displaySiteName: String?): String {

            var result = ""
            when (collection) {
                "blog" -> {
                    result += "블로그"
                }
                "news" -> {
                    result += "뉴스"
                }
                "cafe" -> {
                    result += "카페"
                }
                "efc" -> {
                    result += "웹문서"
                }
                else -> {
                    result += "웹문서"
                }
            }

            when (displaySiteName) {
                "" -> return result
                else -> return result + " > " + displaySiteName
            }
        }

        fun setDateTime(view: TextView, dateTime: String?) {
            val date = dateTime!!.split("T")
            view.text = date[0]
        }
    }
}