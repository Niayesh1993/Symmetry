package xyz.zohre.data.discovery

import retrofit2.Response
import retrofit2.http.GET
import xyz.zohre.data.model.TrackResponse

interface RemoteDataSource {

    @GET("/v2/5df79a3a320000f0612e0115")
    suspend fun getTracks(): Response<TrackResponse>


    @GET("/v2/5df79b1f320000f4612e011e")
    suspend fun searchTracks(): Response<TrackResponse>
}