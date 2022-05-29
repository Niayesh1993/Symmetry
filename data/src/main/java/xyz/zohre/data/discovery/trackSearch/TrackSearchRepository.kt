package xyz.zohre.data.discovery.trackSearch

import xyz.zohre.data.model.Sessions
import xyz.zohre.data.model.TrackResponse
import xyz.zohre.domain.FlowRepository

interface TrackSearchRepository: FlowRepository<Any, TrackResponse> {

    fun search(tracks: List<Sessions>, element: String, fromIndex: Int, toIndex: Int): Int?

    fun filterTrackList(tracks: List<Sessions>, query: String): List<Sessions>
}