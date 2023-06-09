package com.example.owapp.page

import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.owapp.R
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.util.sleepTime
import com.example.owapp.viewmodel.MineViewModel
import com.example.owapp.viewmodel.TabRankingViewModel
import com.mm.hamcompose.data.bean.PointItem
import com.mm.hamcompose.data.bean.TabTitle
import kotlinx.coroutines.delay

/**
 * Created by Owen on 2023/5/26
 */
@Composable
fun TabRankingPage(navCtrl: NavHostController){
    val tabRankingViewModel:TabRankingViewModel = viewModel()
    if(!tabRankingViewModel.isInit){
        tabRankingViewModel.isInit=true
        tabRankingViewModel.getMyPoint()
        tabRankingViewModel.getPointRankingList()
    }
    val pointsBean=tabRankingViewModel.myPointLiveData.observeAsState().value
    val pointList=tabRankingViewModel.rankListLiveData.observeAsState().value?.collectAsLazyPagingItems()
    val itemList by remember{ tabRankingViewModel.itemList }
    var isLoading  by remember {
        tabRankingViewModel.isLoading
    }
    pointList?.let {
        tabRankingViewModel.sleepTime{
            isLoading=false
        }
    }
    //Handler(Looper.getMainLooper()).postDelayed({ isLoading=false },3000)
   Column (Modifier.fillMaxSize()){
       Card(
           modifier = Modifier
               .padding(start = 10.dp, end = 10.dp, bottom = 15.dp)
               .fillMaxSize()
               .background(color = Color.White, shape = RoundedCornerShape(8.dp))
       ) {
           Column(modifier = Modifier
               .padding(start = 15.dp, end = 15.dp)) {
               Box(
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(60.dp)
               ) {
                   Text(text = "用户", modifier = Modifier.align(Alignment.CenterStart))
                   Text(text = "积分", modifier = Modifier.align(Alignment.Center))
                   Text(text = "排名", modifier = Modifier.align(Alignment.CenterEnd))
               }
               Divider(
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(0.5.dp), color = Color.LightGray
               )
               pointsBean?.let {
                   Box(
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(30.dp)
                   ) {
                       Text(
                           text = it.username,
                           modifier = Modifier.align(Alignment.CenterStart),
                           color = C_Primary
                       )
                       Text(
                           text = it.coinCount,
                           modifier = Modifier.align(Alignment.Center),
                           color = C_Primary
                       )
                       Text(
                           text = it.rank?:"",
                           modifier = Modifier.align(Alignment.CenterEnd),
                           color = C_Primary
                       )
                   }
               }

               if(isLoading){
                   Box(modifier=Modifier.fillMaxSize()){
                       CircularProgressIndicator(
                           color= C_Primary,
                           modifier = Modifier
                               .align(Alignment.Center)
                               .size(48.dp)
                       )
                   }
               }else{
                   //列表
                   LazyColumn() {
                       pointList?.let {
                           itemsIndexed(it){index, bean ->
                               Box(
                                   modifier = Modifier
                                       .fillMaxWidth()
                                       .height(30.dp)
                               ) {
                                   Row(modifier = Modifier
                                       .wrapContentHeight()
                                       .align(Alignment.CenterStart), verticalAlignment = Alignment.CenterVertically){
                                       Text(
                                           text = bean?.username?:"",
                                           color = if(index<3) Color.Black else Color.Gray
                                       )
                                       if(index<3){
                                           Icon(painter = painterResource(id = R.drawable.ic_hot),
                                               contentDescription = "",
                                               tint=Color.Red ,
                                               modifier=Modifier.size(15.dp))
                                       }
                                   }
                                   Text(
                                       text = bean?.coinCount?:"",
                                       modifier = Modifier.align(Alignment.Center),
                                       color =if(index<3) Color.Black else Color.Gray
                                   )
                                   Text(
                                       text = bean?.rank.toString(),
                                       modifier = Modifier.align(Alignment.CenterEnd),
                                       color = if(index<3) Color.Black else Color.Gray
                                   )
                               }

                           }
                       }
                   }
               }
           }
       }
   }
}