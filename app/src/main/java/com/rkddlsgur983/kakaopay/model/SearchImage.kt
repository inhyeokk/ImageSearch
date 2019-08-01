package com.rkddlsgur983.kakaopay.model

import com.google.gson.annotations.SerializedName

data class SearchImage(
    @SerializedName("documents")
    val documents: ArrayList<Document>? = null
) {
    class Document(

        @SerializedName("collection")
        val collection: String = "",

        @SerializedName("datetime")
        val dateTime: String = "",

        @SerializedName("display_sitename")
        val displaySitename: String = "",

        @SerializedName("doc_url")
        val docUrl: String = "",

        @SerializedName("height")
        val height: Int = 0,

        @SerializedName("image_url")
        val imageUrl: String = "",

        @SerializedName("thumbnail_url")
        val thumbnailUrl: String = "",

        @SerializedName("width")
        val width: Int = 0
    ) {
        override fun toString(): String {
            return "Document(collection='$collection', dateTime='$dateTime', displaySitename='$displaySitename', docUrl='$docUrl', height=$height, imageUrl='$imageUrl', thumbnailUrl='$thumbnailUrl', width=$width)"
        }
    }
}