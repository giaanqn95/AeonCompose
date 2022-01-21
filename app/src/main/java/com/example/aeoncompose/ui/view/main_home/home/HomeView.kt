package com.example.aeoncompose.ui.view.main_home.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.example.aeoncompose.R
import com.example.aeoncompose.api.UiState
import com.example.aeoncompose.data.response.PromotionResponse
import com.example.aeoncompose.extensions.onClick
import com.example.aeoncompose.ui.base_view.GridLayout
import com.example.aeoncompose.ui.navigation.EnumProfileScreen
import com.example.aeoncompose.utils.ComposePagerSnapHelper
import com.example.aeoncompose.utils.LogCat
import com.example.aeoncompose.utils.ScreenUtils
import com.example.aeoncompose.utils.ScreenUtils.rdp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import kotlinx.coroutines.delay
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navHostController: NavHostController, homeViewModel: HomeViewModel = hiltViewModel()) {
    val homeBannerState = homeViewModel.uiStateHomeBanner.value
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        // Read document: https://developer.android.com/jetpack/compose/side-effects
        val observer = LifecycleEventObserver { _, event ->
            LogCat.d("HomeScreen ${event.name}")
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    Column {
        HeaderProfile()
        LazyColumn {
            item { SlideBanner(homeBannerState) }
            item { QuickAction(navHostController) }
            item { BodyContent() }
        }
    }
}

@Composable
private fun HeaderProfile() {
    ConstraintLayout(
        modifier = Modifier
            .wrapContentHeight()
            .padding(start = 0.rdp, top = 0.rdp, bottom = 0.rdp, end = 0.rdp)
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
                .size(50.rdp)
                .constrainAs(imgAvatar) {
                    centerVerticallyTo(parent)
                    start.linkTo(parent.start, margin = 15.rdp)
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

@ExperimentalCoilApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SlideBanner(uiState: UiState<PromotionResponse>) {
    val promotion = uiState.result ?: PromotionResponse()
    AnimatedVisibility(visible = !promotion.isEmpty(), enter = fadeIn()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.bg_header_2),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )

            ComposePagerSnapHelper(width = ScreenUtils.screenWidth.rdp) { state ->
                LaunchedEffect(true) {
                    repeat(Int.MAX_VALUE) {
                        delay(1000)
                        if (state.layoutInfo.totalItemsCount == 0) return@repeat
                        state.animateScrollToItem(index = it % state.layoutInfo.totalItemsCount)
                    }
                }
                LazyRow(
                    modifier = Modifier
                        .padding(10.rdp)
                        .clip(RoundedCornerShape(5.rdp)),
                    state = state
                ) {
                    items(promotion) { item ->
                        Box(
                            modifier = Modifier
                                .background(Color.DarkGray)
                                .fillParentMaxWidth()
                        ) {
                            Image(
                                painter = rememberImagePainter(
                                    data = item.url_image,
                                    builder = {
                                        size(OriginalSize)
                                    }
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillParentMaxSize()
                                    .onClick {

                                    },
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickAction(navHostController: NavHostController) {
    FlowRow(
        mainAxisSize = SizeMode.Expand,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.rdp)
    ) {
        repeat(8) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .height(95.rdp)
                    .background(Color.Blue, CircleShape)
                    .onClick { navHostController.navigate(EnumProfileScreen.getName()) },
                contentAlignment = Alignment.Center,
            ) {
                Text(it.toString())
            }
        }
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    GridLayout(
        modifier = modifier
            .padding(10.rdp)
            .background(Color.White)
    ) {
        repeat(100) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .height(95.rdp)
                    .background(Color.Green, CircleShape),
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