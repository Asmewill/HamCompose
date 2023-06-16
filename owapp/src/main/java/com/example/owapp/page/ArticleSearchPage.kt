package com.example.owapp.page

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.owapp.R
import com.example.owapp.room.HotKey
import com.example.owapp.route.RouteName
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.ui.theme.ToolBarHeight
import com.example.owapp.util.Constant
import com.example.owapp.viewmodel.ArticleSearchViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.mm.hamcompose.data.bean.WebData

/**
 * Created by Owen on 2023/5/25
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ArticleSearchPage(navCtrl: NavHostController) {
    val mContext= LocalContext.current
    val mViewModel: ArticleSearchViewModel = viewModel()
    if(!mViewModel.isInit){
        mViewModel.isInit=true
        mViewModel.getHotkey()
        mViewModel.getSearchHistoryList(mContext = mContext)
    }
    val hotList=mViewModel.hotkeyLiveData.observeAsState().value
    val historyList=mViewModel.historyLiveData.observeAsState().value?.reversed()
    val searchList=mViewModel.searchLiveData.observeAsState().value?.collectAsLazyPagingItems()
    val keyboardController = LocalSoftwareKeyboardController.current

    var inputText by remember { mutableStateOf("") }
    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color.White)) {
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
                                        text = "请输入想要搜索的内容",
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
                        .padding(start = 10.dp, top = 5.dp,end=30.dp)  //padding在background之后的话，那就是内部padding
                )
                if(!TextUtils.isEmpty(inputText)){
                    Icon(imageVector =Icons.Default.Clear, contentDescription ="",
                        modifier= Modifier.padding(end = 10.dp).align(Alignment.CenterEnd).size(15.dp).clickable {
                            inputText=""

                    }, tint = Color.Gray)
                }

            }

            TextButton(modifier = Modifier
                .height(ToolBarHeight)
                .width(50.dp), onClick = {
                 mViewModel.addRecord(mContext, HotKey(inputText))
                 mViewModel.getSearchList(inputText)
                keyboardController?.hide()

            }) {
                Text(text = "搜索", color = Color.White)

            }
        }

        LazyColumn() {
            hotList?.let { hotList->
                //搜索热词
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .background(color = Color.White)
                            .padding(horizontal = 10.dp),//在background之后属于内部padding
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(
                            modifier = Modifier
                                .width(5.dp)
                                .height(16.dp)
                                .background(color = Color.Black)
                        )
                        Text(
                            text = "搜索热词",
                            color = Color.Black,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }
                item {
                    FlowRow(Modifier.padding(horizontal = 10.dp)) {
                        hotList.forEachIndexed { index, hotkey ->
                            Box(
                                modifier = Modifier
                                    .padding(end = 6.dp, bottom = 6.dp)//这里的padding是margin
                                    .height(28.dp)
                                    .clip(shape = RoundedCornerShape(14.dp)) //先裁剪，在给背景颜色
                                    .background(color = C_Primary)
                                    .clickable {
                                        mViewModel.getSearchList("${hotkey.name}")
                                    }
                                    .padding(//在Background之后的padding才是真正的padding
                                        start = 15.dp,
                                        end = 15.dp
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "${hotkey.name}", color = Color.White, fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
            historyList?.run {
                if(this.isNotEmpty()){
                    //搜索历史
                    stickyHeader {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(36.dp)
                                .background(color = Color.White)
                                .padding(horizontal = 10.dp),//在background之后属于内部padding
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .width(5.dp)
                                    .height(16.dp)
                                    .background(color = Color.Black)
                            )
                            Text(
                                text = "搜索历史",
                                color = Color.Black,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "清空",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                modifier = Modifier.clickable {
                                    mViewModel.removeAllRecord(mContext)
                                })
                        }
                    }
                    itemsIndexed(this) { index,history->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    mViewModel.getSearchList("${history.text}")
                                    inputText = "${history.text}"
                                    keyboardController?.hide()
                                }
                                .padding(vertical = 5.dp, horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_time),
                                contentDescription = "",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "${history.text}",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        mViewModel.removeRecord(mContext, history)
                                    },
                                tint = Color.Gray)
                        }
                    }
                }
            }
            searchList?.apply {
                if(this.itemCount>0){
                    //搜索结果
                    stickyHeader {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(36.dp)
                                .background(color = Color.White)
                                .padding(horizontal = 10.dp),//在background之后属于内部padding
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .width(5.dp)
                                    .height(16.dp)
                                    .background(color = Color.Black)
                            )
                            Text(
                                text = "搜索结果",
                                color = Color.Black,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }
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
                            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
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
                                        Icon(imageVector=Icons.Default.FavoriteBorder,
                                            tint = Color.Gray,contentDescription ="",modifier=Modifier.align(Alignment.CenterEnd))
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