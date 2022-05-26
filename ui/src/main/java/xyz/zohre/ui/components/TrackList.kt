package xyz.zohre.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import xyz.zohre.data.model.Sessions


@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun TrackList(
    loading: Boolean,
    sessions: List<Sessions>,
    onChangeScrollPosition: (Int) -> Unit,
    page: Int,
    onTriggerNextPage: () -> Unit,
){
    Box(modifier = Modifier
        .background(color = MaterialTheme.colors.surface)
    ) {
        if (loading && sessions.isEmpty()) {
            HorizontalDottedProgressBar()
            LoadingRecipeListShimmer(imageHeight = 250.dp,)
        }
        else if(sessions.isEmpty()){
            NothingHere()
        }
        else {
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                Modifier.background(Color.Black),
                content = {
                    itemsIndexed(
                        items = sessions
                    ) { index, session ->
                        onChangeScrollPosition(index)
                        if ((index + 1) >= (page * sessions.size/2) && !loading) {
                            onTriggerNextPage()
                        }
                        TrackItem(
                            sessions = session
                        )
                    }
                }
            )
        }
    }
}







