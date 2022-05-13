package xyz.zohre.data.model

import com.squareup.moshi.Json

data class Sessions(
    @Json(name = "name") val name : String,
    @Json(name = "listener_count") val listener_count : Int,
    @Json(name = "genres") val genres : List<String>,
    @Json(name = "current_track") val current_track : CurrentTrack
)
