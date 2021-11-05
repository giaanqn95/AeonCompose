package com.example.aeoncompose.ui.view.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.aeoncompose.R
import com.example.aeoncompose.utils.CircularList
import com.example.aeoncompose.utils.ComposePagerSnapHelper
import com.example.aeoncompose.utils.ScreenUtils
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import kotlinx.coroutines.delay
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navHostController: NavHostController) {
    Column {
        HeaderProfile()
        LazyColumn {
            item { SlideBanner() }
            item { QuickAction() }
        }
    }
}

@Composable
private fun HeaderProfile() {
    ConstraintLayout(
        modifier = Modifier
            .wrapContentHeight()
            .padding(start = 0.dp, top = 0.dp, bottom = 0.dp, end = 0.dp)
    ) {
        val (imgAvatar, vDivider, tvName, tvPoint) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.bg_header_1),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Image(
            painter = painterResource(id = R.drawable.logo_member),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
                .constrainAs(imgAvatar) {
                    centerVerticallyTo(parent)
                    start.linkTo(parent.start, margin = 15.dp)
                }
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(vDivider) {
                centerTo(parent)
            })

        Text(text = "Name", modifier = Modifier.constrainAs(tvName) {
            start.linkTo(imgAvatar.end)
            bottom.linkTo(vDivider.top)
        })

        Text(text = "6 points", modifier = Modifier.constrainAs(tvPoint) {
            start.linkTo(imgAvatar.end)
            top.linkTo(vDivider.bottom)
        })
    }
}

@Composable
private fun SlideBanner() {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        val items = CircularList(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
        Image(
            painter = painterResource(id = R.drawable.bg_header_2),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        ComposePagerSnapHelper(width = ScreenUtils.screenWidth.dp) { state ->
            LaunchedEffect(true) {
                repeat(Int.MAX_VALUE) {
                    delay(1000)
                    state.animateScrollToItem(index = it % state.layoutInfo.totalItemsCount)
                }
            }
            LazyRow(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(5.dp)), state = state
            ) {
                items(items) { item ->
                    Box(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .height(150.dp)
                            .background(randomColor())
                    ) {
                        Text(text = item.toString(), modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickAction() {
    FlowRow(
        mainAxisSize = SizeMode.Expand,
        mainAxisSpacing = 5.dp,
        crossAxisSpacing = 5.dp,
        modifier = Modifier.padding(10.dp)
    ) {
        repeat(8) {
            Box(
                modifier = Modifier
                    .size((ScreenUtils.screenWidth / 4).dp - 9.dp)
                    .background(Color.Blue)
                    .border(2.dp, Color.DarkGray),
                contentAlignment = Alignment.Center,
            ) {
                Text(it.toString())
            }
        }
    }
}

fun randomColor(): Color {
    val rnd = Random()
    return Color(255, rnd.nextInt(256), rnd.nextInt(256))
}