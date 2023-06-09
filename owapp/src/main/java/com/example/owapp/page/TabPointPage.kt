package com.example.owapp.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.owapp.R
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.viewmodel.TabPointViewModel
import com.example.owapp.viewmodel.TabRankingViewModel
import com.mm.hamcompose.data.bean.SignBean

/**
 * Created by Owen on 2023/5/26
 */
@Composable
fun TabPointPage(navCtrl: NavHostController) {
    val tabPointViewModel: TabPointViewModel = viewModel()
    if(!tabPointViewModel.isInit){
        tabPointViewModel.isInit=true
        tabPointViewModel.getPointRecordList()
    }
    val recordList= tabPointViewModel.recordLiveData.observeAsState().value?.collectAsLazyPagingItems()
    val itemList by remember {
        mutableStateOf(
            value = mutableListOf(
                SignBean("2023-04-27 17:32:12 签到,积分:10+", "签到"),
                SignBean("2023-04-27 17:32:12 分享文章,积分:10+", "分享文章"),
                SignBean("2023-04-27 17:32:12 签到,积分:10+", "签到"),
                SignBean("2023-04-27 17:32:12 签到,积分:10+", "签到"),
                SignBean("2023-04-27 17:32:12 评论,积分:10+", "评论文章"),
            )
        )
    }
    Column(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            Card(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 15.dp)
                    .fillMaxSize()
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    ) {
                        Text(
                            text = "积分详情",
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                        Text(
                            text = "合计：606",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                    //列表
                    LazyColumn() {
                        recordList?.let {
                            itemsIndexed(recordList){index,item->
                                Row(modifier= Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(50.dp)
                                    ) {
                                        Text(
                                            text = item?.desc?:"",
                                            modifier = Modifier
                                                .align(Alignment.CenterStart)
                                                .padding(end = 100.dp),
                                            color = if (index < 3) Color.Black else Color.Gray,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            text = item?.reason?:"",
                                            modifier = Modifier.align(Alignment.CenterEnd),
                                            color = if (index < 3) Color.Black else Color.Gray,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                                Divider(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(0.2.dp),
                                    color = Color.LightGray
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}
