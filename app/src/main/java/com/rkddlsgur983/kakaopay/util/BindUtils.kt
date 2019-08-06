package com.rkddlsgur983.kakaopay.util

class BindUtils {

    companion object {

        @JvmStatic
        fun setTitleUrl(url: String): String {
            return ""
        }

        @JvmStatic
        fun setCollection(collection: String, displaySiteName: String): String {

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

        @JvmStatic
        fun setDateTime(dateTime: String): String {
            val date = dateTime.split("T")
            return date[0]
        }
    }
}