package com.example.owapp.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.owapp.R
/**
 * Created by Owen on 2023/6/14
 */
@Composable
fun EmptyView() {
//    软键盘弹起时--->固定不动
//    Box(modifier=Modifier.fillMaxSize(1f)){
//        Column(modifier=Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment =Alignment.CenterHorizontally) {
//
//            Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription ="", tint = Color.DarkGray, modifier = Modifier.size(25.dp) )
//            Spacer(modifier = Modifier.height(10.dp))
//            Text(text = "输入关键字搜索", fontSize = 14.sp,color=Color.DarkGray)
//        }
//    }
//    软键盘弹起时--->自动弹起
    Column(modifier=Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment =Alignment.CenterHorizontally) {

        Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription ="", tint = Color.DarkGray, modifier = Modifier.size(25.dp) )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "输入关键字搜索", fontSize = 14.sp,color=Color.DarkGray)
    }

}