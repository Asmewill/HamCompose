package com.example.owapp.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.Pager
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.route.RouteName
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.viewmodel.CategoryViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
/**
 * Created by Owen on 2023/5/19
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun CategoryPage(navCtrl:NavHostController, indexPage:Int,onPageSelected:(Int)->Unit){
    val categoryViewModel=CategoryViewModel()
    val titleList by remember { categoryViewModel.titles}
    val scrope= rememberCoroutineScope()

    
    Column() {
        val  pageState= rememberPagerState(
            pageCount = titleList.size,
            initialPage = indexPage,
            initialOffscreenLimit=titleList.size
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
            Spacer(modifier = Modifier.weight(1.0f))
            Icon(imageVector = Icons.Default.Add, contentDescription = "", tint = Color.White,modifier= Modifier
                .padding(end = 10.dp)
                .clickable {
                    navCtrl.navigate(RouteName.SHARE_ARTICLE)
                })
        }
        HorizontalPager(state = pageState) {page->
            onPageSelected(pageState.currentPage)
            when(page){
                0-> StructureTreePage(navCtrl = navCtrl)
                1-> NaviPage(navCtrl = navCtrl)
                2-> WeChatPage(navCtrl = navCtrl)
            }
        }
    }
}