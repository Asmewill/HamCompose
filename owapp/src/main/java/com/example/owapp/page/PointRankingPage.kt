package com.example.owapp.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.util.HamToolBar1
import com.example.owapp.viewmodel.PointRankingViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Owen on 2023/5/25
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun PointRankingPage(navCtrl:NavHostController) {
    val pointRankingViewModel:PointRankingViewModel = viewModel()
    val titleList by remember {  pointRankingViewModel.titleList}
    var selectIndex by remember { mutableStateOf(0)}
    val scope= rememberCoroutineScope()
    val pageState = rememberPagerState(
        pageCount = titleList.size,
        initialPage=selectIndex,
        initialOffscreenLimit=titleList.size
    )
    Column(
        Modifier
            .fillMaxSize()
            .background(color = C_Primary)) { //积分排行榜
        //TopBar
        HamToolBar1(middleTitle = "积分排行", onBack = {
            navCtrl.navigateUp()
        })
        //MiddleTab
        Box(modifier= Modifier
            .fillMaxWidth()
            .height(60.dp), contentAlignment = Alignment.Center) {
            Row(modifier = Modifier.wrapContentWidth()){
                titleList.forEachIndexed { index, tabTitle ->
                    val textColor=if(index==selectIndex) Color.Black else Color.LightGray
                    val fontWeight= if(index==selectIndex) FontWeight.W300 else  FontWeight.Normal
                    Text(text =tabTitle.text,color=textColor, fontWeight = fontWeight , modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .clickable {
                            selectIndex = index
                            //页面切换
                            scope.launch(context = Dispatchers.IO) {
                                pageState.scrollToPage(selectIndex)
                            }
                        })
                    if(index==0){
                        Divider(modifier = Modifier
                            .width(1.dp)
                            .height(16.dp)
                            .align(Alignment.CenterVertically), color = Color.Gray)
                    }
                }
            }
        }
        HorizontalPager(state = pageState) { page->
            selectIndex=pageState.currentPage
            when(page){
                0->{
                    TabRankingPage(navCtrl = navCtrl)
                }
                1->{
                    TabPointPage(navCtrl = navCtrl)
                }
            }
        }
    }
}