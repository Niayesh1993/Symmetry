package xyz.zohre.ui.trackList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import xyz.zohre.ui.components.TrackList
import xyz.zohre.ui.components.SearchAppBar
import xyz.zohre.ui.components.util.SnackbarController
import xyz.zohre.ui.theme.AppTheme
import xyz.zohre.ui.theme.Black1


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TrackListFragment: Fragment() {

    private val snackbarController = SnackbarController(lifecycleScope)

    private val viewModel: TrackListViewModel by viewModels()

    @SuppressLint("StateFlowValueCalledInComposition")
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {

                val tracks = viewModel.tracks

                val query = viewModel.query.value

                val loading = viewModel.loading.value

                val page = viewModel.page.value

                val scaffoldState = rememberScaffoldState()

                AppTheme(
                    displayProgressBar = loading,
                    scaffoldState = scaffoldState,
                    darkTheme = false,
                ) {

                    Scaffold(
                        topBar = {
                            Row(modifier = Modifier.fillMaxWidth()){
                                TopAppBar(title = {
                                    Text(
                                        "Discovery",
                                        color = Color.White,
                                    )
                                }, backgroundColor = Black1)
                            }
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {
                                    viewModel.searchTracks(query)
                                },
                                )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        },
                        ) {
                        TrackList(
                            loading = loading,
                            sessions = tracks.value,
                            onChangeScrollPosition = viewModel::onChangeRecipeScrollPosition,
                            page = page,
                            onTriggerNextPage = { viewModel.loadTracks() }
                        )
                    }
                }
            }
        }
    }
}