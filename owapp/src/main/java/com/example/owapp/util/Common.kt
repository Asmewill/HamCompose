package com.example.owapp.util

import android.text.TextUtils
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.ui.theme.ToolBarHeight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Owen on 2023/5/26
 */
@Composable
fun HamToolBar1(
    middleTitle:String,
    onBack:(()->Unit)?=null,
    rightText:String?="",
    onRightTextClick:(()->Unit)?=null,
    rightDrawableId:Int=-1,
    onRightDrawableClick:(()->Unit)?=null
){

    Box(modifier= Modifier
        .fillMaxWidth()
        .height(ToolBarHeight)
        .background(color = C_Primary).padding(end = 10.dp)) {
        //文字居中
        Text(text=middleTitle, maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 16.sp,
            fontWeight = FontWeight.W500, color= Color.White,
            modifier=Modifier.padding(start = 50.dp, end = 50.dp).align(Alignment.Center))//相对于Box居中显示
        Row(modifier=Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically){
            //左边返回按钮
            if(onBack!=null){
                Box(modifier = Modifier.height(48.dp).width(54.dp).clickable {
                    onBack.invoke()
                }, contentAlignment = Alignment.Center){
                    Icon(imageVector=Icons.Default.ArrowBack,contentDescription ="",tint = Color.White)
                }

            }
            Spacer(modifier = Modifier.weight(1f))
            //右边文字
            if(!TextUtils.isEmpty(rightText)){
                Text(text=rightText!!,fontSize = 14.sp,color= Color.White,modifier=Modifier.padding(end = 12.dp).clickable {
                    onRightTextClick?.invoke()
                })
            }
            //右边图标
            if(rightDrawableId!=-1){
                Icon(painter = painterResource(id=rightDrawableId),contentDescription ="",tint = Color.White, modifier = Modifier.padding(start = 12.dp))
                onRightDrawableClick?.invoke()
            }
        }
    }
}
@Composable
fun HamToolBar2(
    middleTitle:String,
    onBack:(()->Unit)?=null,
    rightText:String?="",
    onRightTextClick:(()->Unit)?=null,
    rightImageVetor:ImageVector?=null,
    onRightDrawableClick:(()->Unit)?=null
){

    Box(modifier= Modifier
        .fillMaxWidth()
        .height(ToolBarHeight)
        .background(color = C_Primary).padding(end = 10.dp)) {
        //文字居中
        Text(text=middleTitle,fontSize = 16.sp, fontWeight = FontWeight.W500, color= Color.White,modifier=Modifier.align(Alignment.Center))//相对于Box居中显示
        Row(modifier=Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically){
            //左边返回按钮
            if(onBack!=null){
                Box(modifier = Modifier.height(48.dp).width(54.dp).clickable {
                    onBack.invoke()
                }, contentAlignment = Alignment.Center){
                    Icon(imageVector=Icons.Default.ArrowBack,contentDescription ="",tint = Color.White)
                }

            }
            Spacer(modifier = Modifier.weight(1f))
            //右边文字
            if(!TextUtils.isEmpty(rightText)){
                Text(text=rightText!!,fontSize = 14.sp,color= Color.White,modifier=Modifier.padding(end = 12.dp).clickable {
                    onRightTextClick?.invoke()
                })
            }
            //右边图标
            if(rightImageVetor!=null){
                Icon(imageVector = rightImageVetor,contentDescription ="",tint = Color.White, modifier = Modifier.padding(start = 12.dp).clickable {
                    onRightDrawableClick?.invoke()
                })
            }
        }
    }
}


fun ViewModel.sleepTime(millis: Long = 1500, block: () -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        Thread.sleep(millis)
        block()
    }
}