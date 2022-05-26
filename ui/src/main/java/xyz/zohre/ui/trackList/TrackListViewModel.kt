package xyz.zohre.ui.trackList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import xyz.zohre.data.discovery.trackList.TrackListRepository
import xyz.zohre.data.model.Sessions
import xyz.zohre.data.model.TrackResponse
import xyz.zohre.domain.entities.ApiResult
import kotlinx.coroutines.flow.collectLatest
import xyz.zohre.data.discovery.trackSearch.TrackSearchRepository
import javax.inject.Inject

@HiltViewModel
class TrackListViewModel@Inject constructor(
    private val trackListRepository: TrackListRepository,
    private val trackSearchRepository: TrackSearchRepository
    ): ViewModel()
{

    val tracks: MutableState<List<Sessions>> = mutableStateOf(ArrayList())

    private val _loadingState = MutableStateFlow(true)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _errorState = MutableStateFlow("")
    val errorState: StateFlow<String> = _errorState

    val query = mutableStateOf("")
    val loading = mutableStateOf(false)

    val page = mutableStateOf(1)

    private var recipeListScrollPosition = 0

    init {
        loadTracks()
    }

    fun loadTracks() {
        loading.value = true
        viewModelScope.launch {
            trackListRepository.execute(Any()).collectLatest {
                showResult(it)
            }
        }
    }

    fun searchTracks(query: String) {
        loading.value = true
        viewModelScope.launch {
            trackListRepository.execute(Any()).collectLatest {
                showResult(it)
            }
        }
    }

    private fun showResult(result: ApiResult<TrackResponse>) {
        when (result) {
            is ApiResult.Loading -> {
                _loadingState.value = true
            }
            is ApiResult.Success -> {
                loading.value = false
                _loadingState.value = false
                appendRecipes(result.data.data.sessions)
            }
            is ApiResult.Error -> {
                loading.value = false
                _loadingState.value = false
                _errorState.value = result.exception.message.toString()
            }
        }

    }

    /**
     * Append new tracks to the current list of recipes
     */
    private fun appendRecipes(sessions: List<Sessions>){
        val current = ArrayList(tracks.value)
        current.addAll(sessions)
        tracks.value = current
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    private fun setQuery(query: String){
        this.query.value = query
    }

    fun onChangeRecipeScrollPosition(position: Int){
        recipeListScrollPosition = position
    }

}