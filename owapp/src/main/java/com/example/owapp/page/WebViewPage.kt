package com.example.owapp.page

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.blankj.utilcode.util.SizeUtils
import com.example.owapp.util.HamToolBar1
import com.example.owapp.R

/**
 * Created by Owen on 2023/5/25
 */
@SuppressLint("UseCompatLoadingForDrawables")
@Composable
fun WebViewPage(navCtrl:NavHostController,title:String,url:String) {
    var isLoadFinish  by remember { mutableStateOf(false)}
    var webViewCtrl:WebViewCtrl? by remember {
        mutableStateOf(null)
    }
    Scaffold(
        topBar = {
            HamToolBar1(middleTitle =title, onBack = {
                navCtrl.navigateUp()
            })
        }
    ) {
        AndroidView(factory = { content->
            FrameLayout(content).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
                )
                val progressView = ProgressBar(context).apply {

                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        SizeUtils.dp2px(26f)
                    )
                    progressDrawable =
                        context.resources.getDrawable(R.drawable.horizontal_progressbar)
                    indeterminateTintList =
                        ColorStateList.valueOf(context.resources.getColor(R.color.purple_700))
                }
                val webView = WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
                addView(webView)
                addView(progressView)
                webViewCtrl = WebViewCtrl(this, url, onWebCall = { isFinish ->
                    isLoadFinish = isFinish
                })
                webViewCtrl?.initSettings()
            }
        })



    }
}