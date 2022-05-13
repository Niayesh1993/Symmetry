package xyz.zohre.data.model

import com.squareup.moshi.Json

data class Data(
    @Json(name = "sessions") val sessions : List<Sessions>
)
