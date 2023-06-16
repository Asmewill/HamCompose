package com.example.owapp.page

import android.os.Bundle
import android.text.TextUtils
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.JsonUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.R
import com.example.owapp.event.Event
import com.example.owapp.route.RouteName
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.ui.theme.white1
import com.example.owapp.ui.theme.white3
import com.example.owapp.util.Constant
import com.example.owapp.util.HamToolBar2
import com.example.owapp.viewmodel.MyCollectViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.mm.hamcompose.data.bean.ParentBean
import com.mm.hamcompose.data.bean.WebData

/**
 * Created by Owen on 2023/5/25
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyCollectPage(navCtrl:NavHostController) {
    val myCollectViewModel:MyCollectViewModel = viewModel()
    if(!myCollectViewModel.isInit){
        myCollectViewModel.isInit=true
        myCollectViewModel.getCollectUrlList()
        myCollectViewModel.getArticleList()
    }
    val urlList=myCollectViewModel.urlListLiveData.observeAsState().value
    val articleList=myCollectViewModel.articleLiveData.observeAsState().value?.collectAsLazyPagingItems()
    var labelItem  by remember {    mutableStateOf<ParentBean?>(null)   }

    //保存网址或者文章之后，刷新页面
    LaunchedEffect(key1 = Event.messgeEvent.value.type){
        if(Event.messgeEvent.value.type==1){
            ToastUtils.showLong("PostBean:"+GsonUtils.toJson(Event.messgeEvent.value))
            myCollectViewModel.getCollectUrlList()
        }else if(Event.messgeEvent.value.type==2){
            ToastUtils.showLong("PostBean:"+GsonUtils.toJson(Event.messgeEvent.value))
            myCollectViewModel.getArticleList()
        }
        Event.messgeEvent.value.type=0 //初始化到原来的值,防止一进入页面时调用
    }
    var isShowDialog  by remember { mutableStateOf(false)}
    //长按对话框
    if(isShowDialog){
        Dialog(onDismissRequest = { isShowDialog=false}) {
            Card() {
                Column(modifier= Modifier
                    .fillMaxWidth(0.95f)
                    .padding(20.dp)) {
                    Text(text = "提示",color=Color.Black, fontSize = 16.sp)
                    Text(text = "请选择一下操作",color=Color.Gray,fontSize = 14.sp,modifier=Modifier.padding(top = 10.dp, bottom = 10.dp))
                    Box(modifier= Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(color = C_Primary)
                        .clickable {
                            labelItem?.let {
                                myCollectViewModel.deleteWebsite(it.id)
                            }
                            isShowDialog=false
                        }){
                        Text(text ="删除" , fontSize = 14.sp, modifier=Modifier.align(Alignment.Center))
                    }
                    Box(modifier= Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .height(35.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(color = white3)
                        .clickable {
                            //ToastUtils.showLong("编辑")
                            isShowDialog=false
                            navCtrl.currentBackStackEntry?.replaceArguments(Bundle().apply {
                                putParcelable(Constant.ARGS,labelItem)
                            })
                            navCtrl.navigate(RouteName.EDIT_COLLECT){
                                launchSingleTop=true
                                restoreState=true
                            }
                        }){
                        Text(text ="编辑", fontSize = 14.sp ,modifier=Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }

    //UI界面
    Column(
        Modifier
            .fillMaxSize()
            .background(color = white1)) {
        //TopBar
        HamToolBar2(middleTitle = "我的收藏",onBack={
              navCtrl.navigateUp()
        }, rightImageVetor = Icons.Default.Add, onRightDrawableClick = {
              navCtrl.navigate(RouteName.ADD_COLLECT)
        })
        LazyColumn(modifier=Modifier.fillMaxSize()){
            urlList?.let {
                stickyHeader {
                    //我的网址
                    Row(modifier= Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .background(color = white1)
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically){
                        Spacer(
                            Modifier
                                .width(5.dp)
                                .height(16.dp)
                                .background(color = Color.Black))
                        Text(text = "我的网址" ,color=Color.Black, fontSize = 16.sp,modifier=Modifier.padding(start = 10.dp))
                    }
                }
                item {
                    Row(modifier=Modifier.fillMaxWidth()){
                        FlowRow(modifier=Modifier.padding(10.dp)) {
                            urlList.forEachIndexed(){index, parentBean ->
                                Text(
                                    text = parentBean.name?:"",
                                    fontSize=12.sp,
                                    modifier = Modifier
                                        .padding(end = 8.dp, bottom = 8.dp) // 外边距
                                        .height(25.dp)
                                        .clip(
                                            RoundedCornerShape(12.5.dp)
                                        )
                                        .background(color = C_Primary)
                                        .combinedClickable(
                                            onClick = {
                                                navCtrl.currentBackStackEntry?.replaceArguments(Bundle().apply {
                                                    this.putParcelable(Constant.ARGS,WebData(parentBean.name?:"",parentBean.link?:""))
                                                })
                                               navCtrl.navigate(RouteName.WEBVIEW)

                                            },
                                            onLongClick = {
                                                labelItem= parentBean
                                                isShowDialog = true
                                            }
                                        )
                                        .padding(horizontal = 10.dp, vertical = 4.dp),//内边距
                                    color=Color.White
                                )
                            }
                        }
                    }
                }
            }
            stickyHeader {
                //文章列表
                Row(modifier= Modifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .background(color = white1)
                    .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically){
                    Spacer(
                        Modifier
                            .width(5.dp)
                            .height(16.dp)
                            .background(color = Color.Black))
                    Text(text = "文章列表" ,color=Color.Black, fontSize = 16.sp,modifier=Modifier.padding(start = 10.dp))
                }
            }
            articleList?.let { it ->
                itemsIndexed(it){index,article->
                    Card(modifier= Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(5.dp)).clickable {
                            navCtrl.currentBackStackEntry?.replaceArguments(Bundle().apply {
                                this.putParcelable(Constant.ARGS,WebData(article?.title?:"",article?.link?:""))
                            })
                            navCtrl.navigate(RouteName.WEBVIEW)
                        }){
                        Column(modifier= Modifier
                            .fillMaxSize()
                            .padding(20.dp)) {
                            Box(modifier=Modifier.fillMaxWidth()) {
                                val author=if(TextUtils.isEmpty(article?.author?:"")) "无名" else article?.author?:""
                                Text(author,fontSize = 14.sp,color=Color.Black,modifier=Modifier.align(Alignment.CenterStart))
                                Row(modifier=Modifier.align(Alignment.CenterEnd), verticalAlignment = Alignment.CenterVertically){
                                    Icon(painter = painterResource(id = R.drawable.ic_time), contentDescription ="",tint=Color.Gray,modifier=Modifier.size(15.dp))
                                    Text(text=article?.niceDate?:"", fontSize = 14.sp, color=Color.Gray)
                                }
                            }
                            Text(text = article?.title?:"",
                                fontSize = 14.sp,color=Color.Gray, maxLines = 2, overflow = TextOverflow.Ellipsis,modifier=Modifier.padding(vertical = 10.dp))

                            Box(modifier=Modifier.fillMaxWidth()) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "",modifier= Modifier
                                    .align(Alignment.CenterEnd)
                                    .clickable {
                                        article?.let {
                                            myCollectViewModel.unCollectArticle(
                                                article.id,
                                                article.originId
                                            )
                                        }
                                    },tint=Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}