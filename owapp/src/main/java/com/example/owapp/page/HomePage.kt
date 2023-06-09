package com.example.owapp.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.R
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.viewmodel.HomeViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.owapp.route.RouteName
import com.example.owapp.viewmodel.IndexViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

/**
 * Created by Owen on 2023/5/19
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomePage(
    navCtrl:NavHostController,
    homeIndex:Int=0,
    onPageSelected:(Int)->Unit
){
    val homeViewModel=HomeViewModel()
    val titleList  by remember {   homeViewModel.titles  }
    val scrope= rememberCoroutineScope()
    Column {
        //TopBar
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(C_Primary)){
            Image(
                painter = painterResource(id = R.drawable.wukong),
                contentDescription = "",
                contentScale= ContentScale.FillBounds,
                modifier= Modifier
                    .padding(start = 10.dp)
                    .size(28.dp)
                    .clip(shape = RoundedCornerShape(14.dp))
                    .align(Alignment.CenterVertically)
                    .clickable {
                        ToastUtils.showLong("点击头像")
                    }
            )
            Row(modifier = Modifier
                .padding(start = 10.dp)
                .height(25.dp)
                .weight(1f)
                .align(Alignment.CenterVertically)
                .background(color = Color.White, shape = RoundedCornerShape(12.5.dp))
                .clickable {
                    navCtrl.navigate(RouteName.ARTICLE_SEARCH)
                }){
                Box(Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_search), contentDescription ="",
                    modifier= Modifier
                        .padding(end = 10.dp)
                        .size(15.dp)
                        .align(Alignment.CenterVertically),
                    tint= C_Primary
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_menu_welfare), contentDescription ="",
                modifier= Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .size(25.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        ToastUtils.showLong("Gift")
                    },
                tint= Color.White
            )
        }
        val pageState = rememberPagerState(
            pageCount = titleList.size,
            initialPage =homeIndex,
            initialOffscreenLimit = titleList.size
        )
       //TabView 推荐  广场 问答
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(color = C_Primary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            titleList.forEachIndexed { index, tabTitle ->
                Text(
                    text = tabTitle.text,
                    fontSize = if(index==pageState.currentPage) 20.sp else 15.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .clickable {
                            scrope.launch {
                                pageState.scrollToPage(index)
                            }
                        },
                    fontWeight = if(index==pageState.currentPage) FontWeight.Bold else FontWeight.Normal,
                )
            }
        }
        HorizontalPager(state = pageState, dragEnabled = true) { page: Int ->
            onPageSelected(pageState.currentPage)
            when(page){
                0->{
                    IndexPage(navCtrl)
                }
                1->{
                    SquarePage(navCtrl)
                }
                2->{
                    WenDaPage(navCtrl)
                }
            }
        }
    }
}