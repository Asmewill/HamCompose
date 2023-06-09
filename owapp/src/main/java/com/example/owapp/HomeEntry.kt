package com.example.owapp

import androidx.compose.runtime.*
import com.example.owapp.page.SplashPage
import com.example.owapp.util.MainPage
import com.google.accompanist.insets.ProvideWindowInsets

/**
 * Created by Owen on 2023/5/18
 */
@Composable
fun  HomeEntry(onFinish:()->Unit){
    var isSplash by remember { mutableStateOf(true)}
    if(isSplash){
        SplashPage(){
            isSplash=false
        }
    }else{
        //可以通过 Modifier.navigationBarsPadding()，获取状态栏高度
        ProvideWindowInsets{
            MainPage(onFinish=onFinish)
        }

    }
}