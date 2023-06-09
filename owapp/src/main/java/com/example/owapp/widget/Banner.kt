package com.example.owapp.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToDownIgnoreConsumed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChangeConsumed
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.owapp.R
import com.example.owapp.ui.theme.C_Primary
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mm.hamcompose.data.bean.BannerBean
import kotlinx.coroutines.delay

/**
 * Created by Owen on 2023/6/1
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(
    list:MutableList<BannerBean>,
    @DrawableRes loadImage:Int= R.drawable.no_banner,
    indicatorAlignment: Alignment = Alignment.BottomCenter,
    onClick:(String ,String)->Unit
){
    val pageState = rememberPagerState(
        pageCount = list.size,
        initialPage = 0,
        //预加载的页面个数
        initialOffscreenLimit = 1,
        infiniteLoop = true //可以无线循环
    )
    Box(
        modifier= Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .height(220.dp)
    ){


        if(list.isEmpty()){
            Image(
                painter = painterResource(id = loadImage),
                contentDescription ="",
                modifier=Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }else{
            //LaunchedEffect 方法接受一个或多个键值参数，用于标识依赖项，
            //当这些值发生变化时，该方法会自动取消之前正在进行的异步操作，
            //并启动一个新的异步操作
            LaunchedEffect(pageState.currentPage, block = {
                if(pageState.pageCount>0){
                    delay(3000)
                    pageState.animateScrollToPage(pageState.currentPage+1)
                }
            })
             HorizontalPager(
                 state = pageState,
                 modifier= Modifier
//                     .pointerInput(pageState.currentPage) {
//                         awaitPointerEventScope {
//                             while (true){
//                                 val event=awaitPointerEvent(PointerEventPass.Initial)
//                                 val dragEvent =event.changes.firstOrNull()
//                                 //当前移动手势是否已被消费
//                                 if(dragEvent!!.positionChangeConsumed()){
//                                     return@awaitPointerEventScope
//                                 }else if(dragEvent.changedToDownIgnoreConsumed()){
//                                     //记录下当前的页面索引值
//                                 }
//                             }
//                         }
//                     }
                     .fillMaxSize()
                     .clickable {
                         with(list[pageState.currentPage]) {
                             onClick(this.url ?: "", this.title ?: "")
                         }
                     }){ page->

                 Image(
                     painter = rememberImagePainter(list[page].imagePath),
                     contentDescription = "",
                     contentScale = ContentScale.FillBounds,
                     modifier=Modifier.fillMaxSize()
                 )
             }
        }

        Box(modifier= Modifier
            .align(indicatorAlignment)
            .padding(bottom = 6.dp)){
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                for(i in  list.indices){
                    var mSize = if(pageState.currentPage==i) 7.dp else  5.dp
                    var mColor=if(pageState.currentPage==i)  C_Primary else Color.Gray
                    Box(modifier= Modifier
                        .size(size = mSize)
                        .background(
                            color = mColor,
                            shape = RoundedCornerShape(50)
                        ))
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }

        }
    }
}