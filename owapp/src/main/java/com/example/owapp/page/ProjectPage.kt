package com.example.owapp.page

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite

import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.owapp.event.Event
import com.example.owapp.route.RouteName
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.util.Constant
import com.example.owapp.viewmodel.ProjectViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mm.hamcompose.data.bean.Article
import com.mm.hamcompose.data.bean.ParentBean
import com.mm.hamcompose.data.bean.WebData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Owen on 2023/5/19
 */
@Composable
fun ProjectPage(navCtrl:NavHostController){
    val mContext= LocalContext.current
    val projectViewModel:ProjectViewModel = viewModel()
    if(!projectViewModel.isInit){
        projectViewModel.isInit=true
        projectViewModel.getProjectTabList()
    }
    val projectList= projectViewModel.projectLiveData.observeAsState().value
    var mIndex by remember{ projectViewModel.tabIndex}
    val projectItemList= projectViewModel.projectItemListLiveData.observeAsState().value?.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false)}
    val refreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    val collectType by remember {
        projectViewModel.collectType
    }
    var collectItem  by remember {
        mutableStateOf<Article?>(null)
    }
    //收藏成功与否回调
    if(collectType==1){
        collectItem?.let {
            it.collect=true
        }
    }else if(collectType==2){
        collectItem?.let {
            it.collect=false
        }
    }
    Column(modifier=Modifier.fillMaxSize()) {
        //TabRow
        projectList?.let {
            if(it.isNotEmpty()){
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
                                projectViewModel.tabIndex.value=index
                                projectViewModel.getProjectItemList(item.id)
                            })
                    }
                }
            }
        }
        //MainList
        SwipeRefresh(
            state = refreshState,
            onRefresh = {
                isRefreshing=true
                projectList?.let {
                    projectViewModel.getProjectItemList(projectList[mIndex].id)
                }
            }){
            LazyColumn(modifier = Modifier.fillMaxSize()){
                projectItemList?.let {
                    scope.launch {
                        delay(500)
                        isRefreshing=false
                    }
                    itemsIndexed(it){index,item->
                        Card(modifier= Modifier
                            .padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .clickable {
                                navCtrl.currentBackStackEntry?.replaceArguments(Bundle().apply {
                                    this.putParcelable(Constant.ARGS,WebData(item?.title?:"",item?.link?:""))
                                })
                                navCtrl.navigate(RouteName.WEBVIEW)
                                projectViewModel.cacheHistory(item!!, mContext = mContext)
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
                                        var imageVector=Icons.Default.FavoriteBorder
                                        var tintColor=Color.Gray
                                        item?.let {
                                            if(item.collect){
                                                imageVector=Icons.Default.Favorite
                                                tintColor= C_Primary
                                            }
                                        }
                                        Icon(imageVector = imageVector, tint = tintColor, contentDescription ="" ,modifier=Modifier.size(22.dp).clickable {
                                            item?.let {
                                                collectItem=it
                                                if(item.collect){
                                                    projectViewModel.uncollectArticleById(item.id)
                                                }else{
                                                    projectViewModel.collectArticleById(item.id)
                                                }
                                            }
                                        })
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

fun getStringData(date:String):String{
    if(date.length>10){
        return date.substring(0,10)
    }else{
        return  date
    }
}
