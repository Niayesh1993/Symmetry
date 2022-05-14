package xyz.zohre.ui.trackList

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import xyz.zohre.data.discovery.trackList.TrackListRepository
import xyz.zohre.data.model.TrackResponse
import xyz.zohre.domain.entities.ApiResult

class FakeTrackListRepository(override val coroutineDispatcher: CoroutineDispatcher): TrackListRepository {

    private lateinit var firstEmit: TrackResponse

    fun setEmits(firstEmit: TrackResponse) {
        this.firstEmit = firstEmit
    }

    override suspend fun execute(parameters: Any): Flow<ApiResult<TrackResponse>> {
        return flow {
            emit(ApiResult.Success(firstEmit))
        }
    }
}