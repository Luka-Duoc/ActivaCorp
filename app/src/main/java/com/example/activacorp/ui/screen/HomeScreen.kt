package com.example.activacorp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch


@Composable
@Preview(showBackground = true)
fun HomeScreen(/*
navController: NavController
*/) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    Scaffold(bottomBar = {
        val coroutineScope = rememberCoroutineScope()
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.LightGray, contentColor = Color.White,
            divider = {},
            indicator = { tabPositions -> TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                height = 2.dp,
                color = MaterialTheme.colorScheme.scrim
            )}
        ) {
            Tab(selected = pagerState.currentPage == 0,
                text = { Text("Eventos") },
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                })
            Tab(selected = pagerState.currentPage == 1,
                text = {
                    Text("Cuenta")
                }, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                })
        }
    }) {
        paddingValues ->  HorizontalPager(state = pagerState, userScrollEnabled = false, contentPadding = paddingValues) { page ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column {
                Text("Test1")
            }
            Text(text = "Hello from Page: $page")
        }

    }
    }
}