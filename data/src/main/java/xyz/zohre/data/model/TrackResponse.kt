package xyz.zohre.data.model

import com.squareup.moshi.Json

data class TrackResponse(
    @Json(name = "data") val data : Data
)
