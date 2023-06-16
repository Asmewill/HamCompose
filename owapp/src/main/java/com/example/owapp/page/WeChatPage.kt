package com.example.owapp.page

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.WeChatViewModel
import com.example.owapp.route.RouteName
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.util.Constant
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 公众号
 * Created by Owen on 2023/5/23
 */

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeChatPage(navCtrl: NavHostController) {
    val weChatViewModel:WeChatViewModel = viewModel()
    if(!weChatViewModel.isInit){
        weChatViewModel.isInit=true
        weChatViewModel.getWeChatList()
    }
    val weChatList=weChatViewModel.weChatLiveData.observeAsState().value
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false)}
    val refreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    SwipeRefresh(
        state = refreshState,
        onRefresh = {
            isRefreshing=true
            weChatViewModel.getWeChatList()
        }){
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.LightGray)
                .padding(start = 10.dp,top=10.dp, end = 10.dp)
        ) {
            weChatList?.let {
                scope.launch {
                    delay(500)
                    isRefreshing=false
                }
                itemsIndexed(it) { index, item ->
                    when (index % 4) {
                        0 -> {
                            Box(modifier = Modifier
                                .padding(bottom = 10.dp)
                                .height(80.dp)
                                .fillMaxWidth()
                                .clickable {
                                    navCtrl.currentBackStackEntry?.replaceArguments(Bundle().apply {
                                        this.putParcelable(Constant.ARGS,item)
                                    })
                                    navCtrl.navigate(RouteName.WECHAT_DETAIL)
                                }
                                .background(
                                    color = C_Primary,
                                    shape = RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)
                                ), contentAlignment = Alignment.Center) {
                                Text(text = item.name?:"", fontSize = 16.sp, color = Color.White)
                            }
                        }
                        1 -> {
                            Box(modifier = Modifier
                                .padding(bottom = 10.dp)
                                .height(80.dp)
                                .fillMaxWidth()
                                .clickable {
                                    navCtrl.currentBackStackEntry?.replaceArguments(Bundle().apply {
                                        this.putParcelable(Constant.ARGS,item)
                                    })
                                    navCtrl.navigate(RouteName.WECHAT_DETAIL)
                                }
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(topEnd = 5.dp, bottomEnd = 5.dp)
                                ), contentAlignment = Alignment.Center) {
                                Text(text = item.name?:"", fontSize = 16.sp, color = Color.Gray)
                            }
                        }
                        2 -> {
                            Box(modifier = Modifier
                                .padding(bottom = 10.dp)
                                .height(80.dp)
                                .fillMaxWidth()
                                .clickable {
                                    navCtrl.currentBackStackEntry?.replaceArguments(Bundle().apply {
                                        this.putParcelable(Constant.ARGS,item)
                                    })
                                    navCtrl.navigate(RouteName.WECHAT_DETAIL)
                                }
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)
                                ), contentAlignment = Alignment.Center) {
                                Text(text = item.name?:"", fontSize = 16.sp, color = Color.Gray)
                            }
                        }
                        3 -> {
                            Box(modifier = Modifier
                                .padding(bottom = 10.dp)
                                .height(80.dp)
                                .fillMaxWidth()
                                .clickable {
                                    navCtrl.currentBackStackEntry?.replaceArguments(Bundle().apply {
                                        this.putParcelable(Constant.ARGS,item)
                                    })
                                    navCtrl.navigate(RouteName.WECHAT_DETAIL)
                                }
                                .background(
                                    color = C_Primary,
                                    shape = RoundedCornerShape(topEnd = 5.dp, bottomEnd = 5.dp)
                                ), contentAlignment = Alignment.Center) {
                                Text(text = item.name?:"", fontSize = 16.sp, color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }


}