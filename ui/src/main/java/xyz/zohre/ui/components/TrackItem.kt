package xyz.zohre.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import io.mockk.InternalPlatformDsl.toStr
import xyz.zohre.data.model.Sessions
import xyz.zohre.ui.theme.Black1

@OptIn(ExperimentalCoilApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TrackItem(sessions: Sessions) {
    Card(
        elevation = 20.dp,
        backgroundColor = Black1,
        modifier =
        Modifier.padding(16.dp)
            .clip(RoundedCornerShape(10.dp))
            .wrapContentHeight()
            .width(184.dp)
    ) {
        Column {
            val (image, title, rating) = createRefs()
            Image(
                contentScale = ContentScale.Crop,
                painter =
                rememberImagePainter(
                    data = sessions.current_track.artwork_url,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = "Image",
                modifier = Modifier
                    .height(183.dp)
                    .width(184.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                Text(
                    text = sessions.name,
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                Text(
                    text = sessions.genres.toStr(),
                    color = Color.White,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 12.sp
                )
            }
            }
        }
    }
