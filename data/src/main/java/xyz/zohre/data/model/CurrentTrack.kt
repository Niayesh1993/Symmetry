package xyz.zohre.data.model

import com.squareup.moshi.Json

data class CurrentTrack(
    @Json(name = "title") val title : String,
    @Json(name = "artwork_url") val artwork_url : String
)
