package com.yuanjingtech

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import yuanjing.composeapp.generated.resources.Res
import yuanjing.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App(
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
) {
    // Determines whether the top app bar should be displayed
    val showTopAppBar = windowSizeClass.windowHeightSizeClass != WindowHeightSizeClass.COMPACT

    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (showTopAppBar) {
                Text("现代企业多租户全栈跨端开发")
            }
            FlowRow {
                Text("欢迎使用Jetpack Compose Multiplatform!")
                Text("${windowSizeClass.windowHeightSizeClass}")
                Text(
                    "Compose Multiplatform!",
                    modifier = Modifier.background(Color.Red)
                        .padding(10.dp)
                        .background(Color.Blue)
                        .padding(5.dp)
                        .background(Color.Yellow)
                )
            }
            Button(onClick = { showContent = !showContent }) {
                Text("点击!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("平台: $greeting")
                }
            }
        }
    }
}

@Composable
fun Row(content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}