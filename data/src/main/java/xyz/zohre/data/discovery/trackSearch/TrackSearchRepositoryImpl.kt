package xyz.zohre.data.discovery.trackSearch

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import xyz.zohre.data.discovery.RemoteDataSource
import xyz.zohre.data.model.Sessions
import xyz.zohre.data.model.TrackResponse
import xyz.zohre.domain.DefaultDispatcher
import xyz.zohre.domain.IoDispatcher
import xyz.zohre.domain.entities.ApiResult
import xyz.zohre.domain.exeption.RemoteCallException
import javax.inject.Inject

class TrackSearchRepositoryImpl@Inject constructor(
    private val remote: RemoteDataSource,
    private val searchDataSource: SearchDataSource,
    @DefaultDispatcher
    override val coroutineDispatcher: CoroutineDispatcher,
    @IoDispatcher
    val ioDispatcher: CoroutineDispatcher
): TrackSearchRepository {

    override suspend fun execute(parameters: Any): Flow<ApiResult<TrackResponse>> {
        var remoteResponse: Response<TrackResponse>? = null
        return flow {
            // start async request so we could use network
            val remoteDeferred = CoroutineScope((ioDispatcher)).async {
                remoteResponse = remote.searchTracks()
            }
            // wait for remote call to complete and emit result
            remoteDeferred.await()
            if (remoteResponse?.isSuccessful == false) {
                emit(ApiResult.Error(RemoteCallException(message = remoteResponse!!.errorBody().toString())))
                return@flow
            }
            val remoteVenues = remoteResponse?.body()
            remoteVenues?.let {
                emit(ApiResult.Success(it))
            }
        }
    }

    override fun search(
        tracks: List<Sessions>,
        element: String,
        fromIndex: Int,
        toIndex: Int,
    ): Int {
        return searchDataSource.search(tracks, element, fromIndex, toIndex)
    }

    override fun filterTrackList(tracks: List<Sessions>, query: String): List<Sessions> {
        val filterList = ArrayList<Sessions>()
        var findIndex = search(tracks, query, 0, tracks.size)
        if (findIndex >= 0) {
            do {
                if (tracks[findIndex].name.take(query.length).compareTo(query, true) == 0) {
                    filterList.add(tracks[findIndex])
                    findIndex ++
                } else break

            } while (findIndex < tracks.size)
        }
        return filterList
    }
}