package xyz.zohre.data.discovery.trackSearch

import xyz.zohre.data.model.TrackResponse
import xyz.zohre.domain.FlowRepository

interface TrackSearchRepository: FlowRepository<Any, TrackResponse> {
}