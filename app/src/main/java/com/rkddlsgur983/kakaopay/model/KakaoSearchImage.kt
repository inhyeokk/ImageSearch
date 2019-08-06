package com.rkddlsgur983.kakaopay.model

import com.google.gson.annotations.SerializedName

data class SearchImage(
    
    val meta: Meta,

    val documents: ArrayList<Document>
)

data class Meta (

    @SerializedName("total_count")
    val totalCount: Int,        // 검색어에 검색된 문서 수

    @SerializedName("pageable_count")
    val pageableCount: Int,     // total_count 중에 노출 가능 문서 수

    @SerializedName("is_end")
    val isEnd: Boolean          // 현재 페이지가 마지막 페이지인지 여부. 값이 false이면 page를 증가시켜 다음 페이지를 요청할 수 있음.
)

data class Document (

    @SerializedName("collection")
    val collection: String,     // 컬렉션

    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,   // 이미지 썸네일 URL

    @SerializedName("image_url")
    val imageUrl: String,       // 이미지 URL

    @SerializedName("width")
    val width: Int,             // 이미지의 가로 크기

    @SerializedName("height")
    val height: Int,            // 이미지의 세로 크기

    @SerializedName("display_sitename")
    val displaySiteName: String,// 출처명

    @SerializedName("doc_url")
    val docUrl: String,         // 문서 URL

    @SerializedName("datetime")
    val dateTime: String        // 문서 작성시간. ISO 8601. [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
) {
    override fun toString(): String {
        return "Document(collection='$collection', dateTime='$dateTime', displaySiteName='$displaySiteName', docUrl='$docUrl', height=$height, imageUrl='$imageUrl', thumbnailUrl='$thumbnailUrl', width=$width)"
    }
}