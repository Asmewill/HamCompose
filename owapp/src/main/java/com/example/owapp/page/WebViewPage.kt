package com.example.owapp.page

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.text.Html
import android.text.TextUtils
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
import com.mm.hamcompose.data.bean.WebData

/**
 * Created by Owen on 2023/5/25
 */
@SuppressLint("UseCompatLoadingForDrawables")
@Composable
fun WebViewPage(navCtrl:NavHostController,title:String="",url:String="",webData:WebData?=null) {
    var isLoadFinish  by remember { mutableStateOf(false)}
    val titleStr= if(!TextUtils.isEmpty(title)) title else webData?.title?:""
    val linkStr= if(!TextUtils.isEmpty(url)) url else webData?.url?:""
    var webViewCtrl:WebViewCtrl? by remember {
        mutableStateOf(null)
    }
    Column {
        HamToolBar1(middleTitle = Html.fromHtml(titleStr).toString(), onBack = {
            navCtrl.navigateUp()
        })
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
                webViewCtrl = WebViewCtrl(this, linkStr, onWebCall = { isFinish ->
                    isLoadFinish = isFinish
                })
                webViewCtrl?.initSettings()
            }
        })
    }

}