package com.example.owapp.page

import android.annotation.SuppressLint
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
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.WeChatViewModel
import com.example.owapp.ui.theme.C_Primary

/**
 * 公众号
 * Created by Owen on 2023/5/23
 */

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeChatPage() {
    val weChatViewModel:WeChatViewModel = viewModel()
    if(!weChatViewModel.isInit){
        weChatViewModel.isInit=true
        weChatViewModel.getWeChatList()
    }
    val weChatList=weChatViewModel.weChatLiveData.observeAsState().value
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .padding(start = 10.dp,top=10.dp, end = 10.dp, bottom = 70.dp)
    ) {
        weChatList?.let {
            itemsIndexed(it) { index, item ->
                when (index % 4) {
                    0 -> {
                        Box(modifier = Modifier
                            .padding(bottom = 10.dp)
                            .height(80.dp)
                            .fillMaxWidth()
                            .clickable {
                                ToastUtils.showLong("Test")
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