package com.example.owapp.page

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.R
import com.example.owapp.route.RouteName
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.ui.theme.ToolBarHeight
import com.example.owapp.util.Constant
import com.example.owapp.viewmodel.StructureListViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mm.hamcompose.data.bean.Article
import com.mm.hamcompose.data.bean.ParentBean
import com.mm.hamcompose.data.bean.WebData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Owen on 2023/6/13
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StructureListPage(navCtrl:NavHostController,parentBean:ParentBean) {
    val mViewModel:StructureListViewModel = viewModel()
    if(!mViewModel.isInit){
        mViewModel.isInit=true
        mViewModel.getStructureList(parentBean.id)
    }
    val structureList=mViewModel.structureLiveData.observeAsState().value?.collectAsLazyPagingItems()
    var inputText by remember { mutableStateOf("") }
    val keyboardController=LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false)}
    val refreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    val collectType by remember {
        mViewModel.collectType
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
        //TopBar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(ToolBarHeight)
                .background(color = C_Primary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(modifier = Modifier
                .height(ToolBarHeight)
                .width(40.dp), onClick = {
                navCtrl.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    tint = Color.White
                )
            }
            Box(modifier= Modifier
                .weight(1.0f)
                .height(30.dp)){
                BasicTextField(
                    singleLine=true,
                    value = inputText,
                    onValueChange = {
                        inputText = it
                    },
                    decorationBox = { innerTextField ->
                        BoxWithConstraints {
                            Box(){
                                innerTextField()
                                if (inputText.isEmpty()) {
                                    Text(
                                        text = "请输入作者名字进行搜索",
                                        fontSize = 12.sp,
                                        color =Color.Gray,
                                        modifier = Modifier.padding(start = 0.dp,top=2.dp)
                                    )
                                }else{
                                    Text(
                                        text = "",
                                        fontSize = 12.sp,
                                        color =Color.Gray,
                                        modifier = Modifier.padding(start = 0.dp,top=2.dp)
                                    )
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                        .padding(start = 10.dp, top = 5.dp, end = 30.dp)  //padding在background之后的话，那就是内部padding
                )
                if(!TextUtils.isEmpty(inputText)){
                    Icon(imageVector =Icons.Default.Clear, contentDescription ="",
                        modifier= Modifier
                            .padding(end = 10.dp)
                            .align(Alignment.CenterEnd)
                            .size(15.dp)
                            .clickable {
                                inputText = ""

                            }, tint = Color.Gray)
                }
            }
            TextButton(modifier = Modifier
                .height(ToolBarHeight)
                .width(50.dp), onClick = {
                if(inputText.isNotEmpty()){
                    mViewModel.searchByAuthor(inputText)
                    keyboardController?.hide()
                }

            }) {
                Text(text = "搜索", color = Color.White)
            }
        }
        SwipeRefresh(
            state = refreshState,
            onRefresh = {
                isRefreshing=true
                if(inputText.isNotEmpty()){
                    mViewModel.searchByAuthor(inputText)
                    keyboardController?.hide()
                }else{
                    mViewModel.getStructureList(parentBean.id)
                }

            }){

            //Middle Content
            LazyColumn(){
                structureList?.apply {
                    scope.launch {
                        delay(500)
                        isRefreshing=false
                    }
                    itemsIndexed(this){index,item->
                        var author =""
                        var  preix=""
                        if(!TextUtils.isEmpty(item!!.author)){
                            author=item!!.author?:""
                        }else{
                            author =item!!.shareUser?:""
                        }
                        if(!TextUtils.isEmpty(author)){
                            preix=author.substring(0,1)
                        }
                        Card(modifier= Modifier
                            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp, top = 5.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .clickable {
                                val bundle = Bundle()
                                bundle.putParcelable(
                                    Constant.ARGS,
                                    WebData(item.title ?: "", item.link ?: "")
                                )
                                navCtrl.currentBackStackEntry?.replaceArguments(bundle)
                                navCtrl.navigate(RouteName.WEBVIEW) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        ) {
                            Column(modifier = Modifier.padding(20.dp)){
                                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                                    Box(modifier= Modifier
                                        .size(20.dp)
                                        .background(color = C_Primary, RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center){
                                        Text(text = "${preix}",color=Color.White, fontSize = 14.sp)
                                    }
                                    Text(text = "${author}",color=Color.Black, fontWeight = FontWeight.W500, fontSize = 15.sp,modifier=Modifier.padding(start = 5.dp))
                                    Spacer(modifier = Modifier.weight(1f))
                                    Icon(painter = painterResource(id = R.drawable.ic_time), tint=Color.Gray,contentDescription ="",modifier=Modifier.size(15.dp))
                                    Text(text = "${item.niceDate}",color=Color.Gray, fontSize = 14.sp)
                                }
                                Text(text = "${Html.fromHtml(item.title)}",
                                    maxLines = 2, overflow = TextOverflow.Ellipsis, color=Color.Gray, fontSize = 14.sp,modifier=Modifier.padding(vertical = 15.dp))
                                Row() {
                                    Box(modifier= Modifier
                                        .height(25.dp)
                                        .background(color = C_Primary, RoundedCornerShape(12.5.dp)), contentAlignment = Alignment.Center){
                                        Text(text = "${item.chapterName}",color=Color.White, fontSize = 14.sp,modifier=Modifier.padding(horizontal = 10.dp))
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Box(modifier= Modifier
                                        .height(25.dp)
                                        .background(color = C_Primary, RoundedCornerShape(12.5.dp)), contentAlignment = Alignment.Center){
                                        Text(text = "${item.superChapterName}",color=Color.White, fontSize = 14.sp,modifier=Modifier.padding(horizontal = 10.dp))
                                    }

                                    Box(modifier=Modifier.fillMaxWidth()){
                                        var imageVector=Icons.Default.FavoriteBorder
                                        var tintColor=Color.Gray
                                        item?.let {
                                            if(item.collect){
                                                imageVector=Icons.Default.Favorite
                                                tintColor= C_Primary
                                            }
                                        }
                                        Icon(imageVector=imageVector,
                                            tint = tintColor,contentDescription ="",modifier=Modifier.align(Alignment.CenterEnd).clickable {
                                                item?.let {
                                                    collectItem=it
                                                    if(item.collect){
                                                        mViewModel.uncollectArticleById(item.id)
                                                    }else{
                                                        mViewModel.collectArticleById(item.id)
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