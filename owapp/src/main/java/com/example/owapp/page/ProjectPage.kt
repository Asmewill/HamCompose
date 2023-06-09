package com.example.owapp.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.rememberImagePainter
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.R
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.viewmodel.ProjectViewModel

/**
 * Created by Owen on 2023/5/19
 */
@Composable
fun ProjectPage(navCtrl:NavHostController){
    val projectViewModel:ProjectViewModel = viewModel()
    if(!projectViewModel.isInit){
        projectViewModel.isInit=true
        projectViewModel.getProjectTabList()
    }
    val projectList= projectViewModel.projectLiveData.observeAsState().value
    var mIndex by remember{ mutableStateOf(0)}
    val projectItemList= projectViewModel.projectItemListLiveData.observeAsState().value?.collectAsLazyPagingItems()
    Column(modifier=Modifier.fillMaxSize()) {
        projectList?.let {
                ScrollableTabRow(
                    selectedTabIndex = mIndex,
                    modifier= Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    edgePadding = 0.dp,
                    backgroundColor = C_Primary,
                    divider=@Composable{},
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .tabIndicatorOffset(tabPositions[mIndex])
                                .height(3.dp)
                                .padding(start = 25.dp, end = 25.dp, bottom = 0.dp),
                            color= Color.White
                        )
                    }
                ) {
                    it.forEachIndexed { index, item ->
                        Tab(text={ Text(text = item.name?:"") },
                            selected = mIndex==index,
                            onClick = {
                            mIndex=index
                            projectViewModel.getProjectItemList(item.id)
                        })
                    }
                }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()){
            projectItemList?.let {
                itemsIndexed(it){index,item->
                    Card(modifier= Modifier
                        .padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)
                        .height(190.dp)
                        .fillMaxWidth()
                        .clickable {
                            ToastUtils.showLong("item clicked")
                        }) {
                        Row {
                            Image(painter = rememberImagePainter(
                                data = item?.envelopePic,
                                builder = {
                                    crossfade(true)
                                    placeholder(R.drawable.no_banner)
                                }), contentDescription = "", contentScale = ContentScale.FillBounds, modifier = Modifier
                                .width(100.dp)
                                .fillMaxHeight())
                            Column(
                                Modifier
                                    .weight(1f)
                                    .padding(start = 5.dp, top = 10.dp, bottom = 10.dp, end = 5.dp)) {
                                Text(
                                    text = item?.title?:"",
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 18.sp,
                                    color = Color.Black,
                                    modifier = Modifier.height(50.dp)
                                )
                                Text(
                                    text = item?.desc?:"",
                                    maxLines = 4,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    modifier= Modifier
                                        .padding(top = 5.dp)
                                        .height(90.dp)
                                )
                                Row(modifier=Modifier.padding(0.dp)) {
                                    Row(modifier=Modifier.weight(1f),verticalAlignment = Alignment.CenterVertically){
                                        Icon(painter = painterResource(id = R.drawable.ic_author), contentDescription = "",
                                            modifier=Modifier.size(15.dp))
                                        Text(text = item?.author?:"",modifier=Modifier.padding(start = 5.dp), fontSize = 12.sp)
                                    }
                                    Row(modifier=Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically){
                                        Icon(painter = painterResource(id = R.drawable.ic_time), contentDescription = "",
                                            modifier=Modifier.size(15.dp))
                                        Text(text = getStringData(item?.niceDate?:""),modifier=Modifier.padding(start = 5.dp),fontSize = 12.sp)
                                    }
                                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription ="" ,modifier=Modifier.size(22.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getStringData(date:String):String{
    if(date.length>10){
        return date.substring(0,10)
    }else{
        return  date
    }
}
