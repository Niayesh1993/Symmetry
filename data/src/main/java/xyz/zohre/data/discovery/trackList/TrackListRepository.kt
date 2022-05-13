package xyz.zohre.data.discovery.trackList

import xyz.zohre.data.model.TrackResponse
import xyz.zohre.domain.FlowRepository

interface TrackListRepository: FlowRepository<Any, TrackResponse>