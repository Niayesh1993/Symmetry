package xyz.zohre.ui.trackSearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import xyz.zohre.data.discovery.trackSearch.TrackSearchRepository
import xyz.zohre.data.model.Sessions
import xyz.zohre.data.model.TrackResponse
import xyz.zohre.domain.entities.ApiResult
import javax.inject.Inject

@HiltViewModel
class TrackSearchViewModel@Inject constructor(private val trackSearchRepository: TrackSearchRepository): ViewModel()
{
    private val _tracks = MutableLiveData<List<Sessions>>()
    val tracks: LiveData<List<Sessions>> get() = _tracks

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun loadMovieDetail() {

        viewModelScope.launch {
            trackSearchRepository.execute(Any()).collect {
                showResult(it)
            }

        }
    }

    private fun showResult(result: ApiResult<TrackResponse>) {
        when (result) {
            is ApiResult.Success -> {
                _tracks.value = result.data.data.sessions
            }
            is ApiResult.Loading -> {
                _loading.value = true

            }
            is ApiResult.Error -> {
                result.exception.let {

                }
            }
        }

    }
}