package xyz.zohre.ui

import xyz.zohre.data.model.*

object TestData {

    val trackResponse = mockTrackResponse(1,
        mockData(1, mutableListOf(
        mockSessions(1, "aaa", "5", listOf("",""), mockCurrentTrack(1, "abc", "url")),
            mockSessions(2, "bbb", "13", listOf("",""), mockCurrentTrack(1, "ghj", "url")),
            mockSessions(3, "ccc", "10", listOf("",""), mockCurrentTrack(1, "frt", "url")),
            mockSessions(4, "ddd", "25", listOf("",""), mockCurrentTrack(1, "ghj", "url"))
    )))

    private fun mockTrackResponse(
        mockId: Int,
        data: Data = mockData(mockId)
    ): TrackResponse {
        return TrackResponse(
            data = data
        )
    }

    private fun mockData(
        mockId: Int,
        sessions: List<Sessions> = listOf(mockSessions(mockId))
    ): Data {
        return Data(
            sessions = sessions
        )
    }
    private fun mockSessions(
        mockId: Int,
        name: String = "id$mockId",
        listener_count: String = "id$mockId",
        genres: List<String> = listOf("id$mockId"),
        current_track: CurrentTrack = mockCurrentTrack(mockId),
    ): Sessions {
        return Sessions(
            name = name,
            listener_count = listener_count.toInt(),
            genres = genres,
            current_track = current_track
        )
    }

    private fun mockCurrentTrack(
        mockId: Int,
        title: String = "id$mockId",
        artwork_url: String = "id$mockId"
    ): CurrentTrack {
        return CurrentTrack(
            title = title,
            artwork_url = artwork_url
        )
    }
}