@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package xyz.zohre.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import io.mockk.InternalPlatformDsl.toStr
import xyz.zohre.data.model.Sessions
import xyz.zohre.ui.theme.Black1
import xyz.zohre.ui.R
import xyz.zohre.ui.R.drawable.*


@OptIn(ExperimentalCoilApi::class, ExperimentalComposeUiApi::class)
@Preview
@Composable
fun TrackItem(sessions: Sessions) {
    Card(
        elevation = 20.dp,
        backgroundColor = Black1,
        modifier =
        Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .wrapContentHeight()
            .width(184.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
        ) {
            val (coverImage, rateImage, title, rating, genres) = createRefs()

            Image(modifier = Modifier
                .constrainAs(coverImage) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .width(184.dp)
                .height(184.dp),
                painter =
                rememberImagePainter(
                    data = sessions.current_track.artwork_url,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = "Image"
            )
            Image(modifier = Modifier
                .constrainAs(rateImage) {
                    start.linkTo(coverImage.start)
                    top.linkTo(coverImage.top)
                }
                .width(25.dp)
                .height(21.dp)
                .background(color = Color.Yellow),
                painter =  painterResource(R.drawable.headphones),
                contentDescription = "Image"
            )
            Text(
                text = sessions.listener_count.toStr(),
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                .constrainAs(rating){
                    start.linkTo(rateImage.end)
                    top.linkTo(coverImage.top)
                }
                    .wrapContentWidth()
                    .height(21.dp)
                    .background(color = Color.Yellow)
            )
            Text(
                    text = sessions.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start)
                        .background(color = Color.Black)
                        .constrainAs(title){
                           start.linkTo(coverImage.start)
                            bottom.linkTo(genres.top)
                        },
                    style = MaterialTheme.typography.h5
                )
            Text(
                    text = sessions.genres.toStr(),
                    modifier = Modifier.padding(2.dp)
                        .background(color = Color.Black)
                        .constrainAs(genres){
                            start.linkTo(coverImage.start)
                            bottom.linkTo(coverImage.bottom)
                        },
                    style = MaterialTheme.typography.caption
                )
        }
        }
    }
