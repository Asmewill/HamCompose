package com.mm.hamcompose.ui

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.*
import com.google.accompanist.insets.ProvideWindowInsets
import com.mm.hamcompose.theme.HamTheme
import com.mm.hamcompose.ui.page.base.HamScaffold
import com.mm.hamcompose.ui.page.splash.SplashPage

@Composable
fun HomeEntry(backDispatcher: OnBackPressedDispatcher) {

    //是否闪屏页
    var isSplash by remember { mutableStateOf(true) }
    if (isSplash) {
        SplashPage { isSplash = false }//闪屏页
    } else {
       HamTheme {
           ProvideWindowInsets() {
               HamScaffold()
           }
       }//主界面
    }

}

