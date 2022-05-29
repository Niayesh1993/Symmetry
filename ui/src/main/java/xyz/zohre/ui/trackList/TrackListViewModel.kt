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

    private val _errorState = MutableStateFlow("")
    val errorState: StateFlow<String> = _errorState

    private lateinit var sortedList: MutableList<Sessions>

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
        resetSearchState()
        viewModelScope.launch {
            trackSearchRepository.execute(Any()).collectLatest {
                sortTrackList(it, query)
            }
        }
    }

    private fun sortTrackList(result: ApiResult<TrackResponse>, query: String) {
        when (result) {
            is ApiResult.Loading -> {
                loading.value = true
            }
            is ApiResult.Success -> {
                loading.value = false
                sortedList = result.data.data.sessions as MutableList<Sessions>
                with(sortedList) { sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name.trim() }) }
                search(query)
            }
            is ApiResult.Error -> {
                loading.value = false
                _errorState.value = result.exception.message.toString()
            }
        }
    }

    private fun search(query: String) {
         appendTracks(trackSearchRepository.filterTrackList(sortedList, query))
    }

    private fun showResult(result: ApiResult<TrackResponse>) {
        when (result) {
            is ApiResult.Loading -> {
                loading.value = true
            }
            is ApiResult.Success -> {
                loading.value = false
                appendTracks(result.data.data.sessions)
            }
            is ApiResult.Error -> {
                loading.value = false
                _errorState.value = result.exception.message.toString()
            }
        }

    }

    /**
     * Append new tracks to the current list of recipes
     */
    private fun appendTracks(sessions: List<Sessions>){
        val current = ArrayList(tracks.value)
        current.addAll(sessions)
        tracks.value = current
    }

    /**
     * Called when a new search is executed.
     */
    private fun resetSearchState() {
        tracks.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
    }

    fun nextPage(){
        // prevent duplicate event due to recompose happening to quickly
        if((recipeListScrollPosition + 1) >= (page.value * tracks.value.size/2) ){
            loading.value = true
            incrementPage()

            if(page.value > 1){
                loadTracks()
            }
            loading.value = false
        }
    }

    fun onQueryChanged(query: String) {
        if(query.isNullOrEmpty()) {
            resetSearchState()
            loadTracks()
        }
        setQuery(query)
    }

    private fun setQuery(query: String){
        this.query.value = query
    }

    private fun incrementPage(){
        setPage(page.value + 1)
    }

    private fun setPage(page: Int){
        this.page.value = page
    }

    fun onChangeRecipeScrollPosition(position: Int){
        recipeListScrollPosition = position
    }

}