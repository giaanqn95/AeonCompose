package com.example.aeoncompose.ui.view.main_home.shopping

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aeoncompose.ui.view.main_home.home.randomColor
import com.example.aeoncompose.utils.ComposePagerSnapHelper
import com.example.aeoncompose.utils.ScreenUtils


@Composable
fun ShoppingScreen(navHostController: NavHostController) {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("TAB 1", "TAB 2", "TAB 3 WITH LOTS OF TEXT")

    Column(modifier = Modifier.padding(top = 25.dp)) {
        TabRow(selectedTabIndex = state) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = state == index,
                    onClick = {
                        state = index
                    }
                )
            }
        }
        ComposePagerSnapHelper(width = ScreenUtils.screenWidth.dp) {
            state = it.firstVisibleItemIndex
            LazyRow(state = it) {
                itemsIndexed(titles) { index, title ->
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(ScreenUtils.screenWidth.dp)
                            .background(randomColor())
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Text tab ${index + 1} selected",
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}