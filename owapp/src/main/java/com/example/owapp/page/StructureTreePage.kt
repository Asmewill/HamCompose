package com.example.owapp.page

import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.owapp.route.RouteName
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.util.Constant
import com.example.owapp.viewmodel.StructureTreeViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 体系
 * Created by Owen on 2023/5/23
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StructureTreePage(navCtrl:NavHostController) {
    val structureTreeViewModel:StructureTreeViewModel= viewModel()
    if(!structureTreeViewModel.isInit){
        structureTreeViewModel.isInit=true
        structureTreeViewModel.getStructureList()
    }
    val tabList by remember {
         structureTreeViewModel.tabList
    }
    val structList = structureTreeViewModel.structLiveData.observeAsState().value
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false)}
    val refreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    SwipeRefresh(
        state = refreshState,
        onRefresh = {
            isRefreshing=true
            structureTreeViewModel.getStructureList()
        }){
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            if(structList!=null&&structList.size>0){
                scope.launch {
                    delay(500)
                    isRefreshing=false
                }

                structList.forEachIndexed { index, parentBean ->
                    stickyHeader {
                        Row(modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 15.dp, end = 10.dp, bottom = 5.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier
                                .width(5.dp)
                                .height(15.dp)
                                .background(color = Color.Black))
                            Text(text = parentBean.name?:"", fontSize=16.sp,color=Color.Black, modifier = Modifier.padding(start = 10.dp))
                        }
                    }
                    item {
                        FlowRow(modifier=Modifier.padding(start = 10.dp, end = 10.dp)){
                            parentBean.children?.let {
                                it.forEachIndexed{index,item->
                                    Box(modifier = Modifier
                                        .padding(start = 5.dp, bottom = 5.dp, top = 5.dp)
                                        .height(30.dp)
                                        .wrapContentWidth()
                                        .clip(shape = RoundedCornerShape(15.dp))
                                        .background(color = C_Primary)
                                        .clickable {
                                            val bundle = Bundle()
                                            bundle.putParcelable(Constant.ARGS, item)
                                            navCtrl.currentBackStackEntry?.replaceArguments(bundle)
                                            navCtrl.navigate(RouteName.STRUCTURE_LIST) {
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }) {
                                        Text(text = item.name?:"", modifier = Modifier
                                            .align(Alignment.Center)
                                            .padding(start = 10.dp, end = 10.dp), fontSize = 14.sp,color=Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}