package com.example.owapp.page

import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.owapp.HamApp.Companion.mContext
import com.example.owapp.R
import com.example.owapp.route.RouteName
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.util.PagingStateUtil
import com.example.owapp.viewmodel.SquareViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mm.hamcompose.data.bean.Article


/**
 * Created by Owen on 2023/5/19
 */
@Composable
fun SquarePage(navCtrl:NavHostController) {
    val mContext = LocalContext.current
    val squareViewModel:SquareViewModel = viewModel() //viewModel()可以保证viewModel只实例化一次
    if(!squareViewModel.isInit){//isInit标量标识是为了防止getHomeList()获取到数据之后，通过MutableLive通知的时候，getSquareList()
        squareViewModel.isInit=true
        squareViewModel.getSquareList()
    }
    val squareList=squareViewModel.squareLiveData.observeAsState().value?.collectAsLazyPagingItems()
    val refreshState= rememberSwipeRefreshState(isRefreshing = false)
    var collectItem  by remember {
        mutableStateOf<Article?>(null)
    }
    val collectType by remember {
        squareViewModel.collectType
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
    PagingStateUtil().pagingStateUtil(
        pagingData = squareList!!,
        refreshState =refreshState ,
        onErrorClick = {
            squareViewModel.getSquareList()
        },
        content = {
            SwipeRefresh(state =refreshState, onRefresh = {
                squareViewModel.getSquareList()
            }) {
                LazyColumn(modifier = Modifier.fillMaxSize()){
                    if(squareList!=null&&squareList.itemCount>0){
                        itemsIndexed(squareList!!){index,item->
                            val author:String=if(TextUtils.isEmpty(item?.author))  item?.shareUser?:"" else  item?.author?:""
                            val authorIndex=if(author.isNotEmpty())author.substring(0,1) else ""
                            Card(
                                modifier = Modifier
                                    .padding(vertical = 5.dp, horizontal = 10.dp).fillMaxWidth()
                                    .background(color = Color.White)
                                    .clickable {
                                        navCtrl.navigate(RouteName.WEBVIEW + "?url=${item?.link}&title=${item?.title}")
                                        squareViewModel.cacheHistory(article = item!!,mContext)
                                    }
                            ) {
                                ConstraintLayout(modifier = Modifier
                                    .padding(20.dp)
                                    .fillMaxWidth()) {
                                    val  (topText1,topText2,topText3,topText4,topIcon1,topIcon2,middleText,tab1,tab2,bottomIcon)=createRefs()
                                    //topText1
                                    Box(modifier = Modifier
                                        .size(25.dp)
                                        .background(color = C_Primary, shape = RoundedCornerShape(12.5.dp))
                                        .constrainAs(topText1) {
                                            top.linkTo(parent.top)
                                            start.linkTo(parent.start)
                                        }){
                                        Text(text = authorIndex, fontSize = 12.sp, color = Color.White, modifier = Modifier.align(
                                            Alignment.Center))
                                    }
                                    //topText2
                                    Text(text = author, fontSize = 14.sp, color = Color.Black, modifier = Modifier
                                        .padding(start = 10.dp)
                                        .constrainAs(topText2) {
                                            top.linkTo(parent.top)
                                            start.linkTo(topText1.end)
                                            bottom.linkTo(topText1.bottom)
                                        })

                                    Row(modifier=Modifier.constrainAs(topText3) {
                                        top.linkTo(parent.top)
                                        start.linkTo(topText2.end)
                                        bottom.linkTo(topText1.bottom)
                                    }, verticalAlignment = Alignment.CenterVertically) {
                                        //topText3
                                        if(item?.fresh==true){
                                            Box(modifier = Modifier
                                                .padding(start = 5.dp)
                                                .wrapContentSize()
                                                .background(
                                                    color = Color.Gray,
                                                    shape = RoundedCornerShape(2.dp)
                                                )
                                            ){
                                                Text(text = "最新", fontSize = 12.sp, color = Color.White, modifier = Modifier
                                                    .padding(
                                                        start = 3.dp,
                                                        top = 1.dp,
                                                        bottom = 1.dp,
                                                        end = 3.dp
                                                    )
                                                    .align(Alignment.Center))
                                            }
                                        }
                                        //HotIcon
                                        if(index<3){
                                            Icon(painter = painterResource(id = R.drawable.ic_hot), tint = Color.Red,
                                                contentDescription = "",modifier= Modifier
                                                    .padding(start = 5.dp)
                                                    .size(18.dp))
                                        }
                                    }
                                    //topText4
                                    Text(text = item?.niceDate?:"", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.constrainAs(topText4){
                                        top.linkTo(parent.top)
                                        end.linkTo(parent.end)
                                        bottom.linkTo(topText1.bottom)
                                    })
                                    //topIcon
                                    Icon(painter = painterResource(id = R.drawable.ic_time), tint = Color.Gray,
                                        contentDescription = "",modifier= Modifier
                                            .padding(end = 2.dp)
                                            .size(15.dp)
                                            .constrainAs(topIcon1) {
                                                top.linkTo(parent.top)
                                                end.linkTo(topText4.start)
                                                bottom.linkTo(topText1.bottom)
                                            })
                                    //middleText
                                    Text(text = item?.title?:"",
                                        fontSize = 14.sp, color = Color.Gray,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 3, modifier = Modifier
                                            .padding(top = 5.dp, bottom = 5.dp)
                                            .constrainAs(middleText) {
                                                top.linkTo(topText1.bottom)
                                            })
                                    //Tab1 , Tab2
                                    Box(modifier = Modifier
                                        .padding(top = 5.dp)
                                        .height(25.dp)
                                        .wrapContentWidth()
                                        .background(color = C_Primary, shape = RoundedCornerShape(12.5.dp))
                                        .constrainAs(tab1) {
                                            bottom.linkTo(parent.bottom)
                                            start.linkTo(parent.start)
                                            top.linkTo(middleText.bottom)
                                        }){
                                        Text(text = item?.superChapterName?:"", fontSize = 12.sp, color = Color.White, modifier = Modifier
                                            .padding(start = 12.dp, end = 12.dp)
                                            .align(Alignment.Center))
                                    }
                                    Box(modifier = Modifier
                                        .padding(start = 8.dp, top = 5.dp)
                                        .height(25.dp)
                                        .wrapContentWidth()
                                        .background(color = C_Primary, shape = RoundedCornerShape(12.5.dp))
                                        .constrainAs(tab2) {
                                            bottom.linkTo(parent.bottom)
                                            start.linkTo(tab1.end)
                                            top.linkTo(middleText.bottom)
                                        }){
                                        Text(text = item?.chapterName?:"", fontSize = 12.sp, color = Color.White, modifier = Modifier
                                            .padding(start = 12.dp, end = 12.dp)
                                            .align(Alignment.Center))
                                    }
                                    var imageVector=Icons.Default.FavoriteBorder
                                    var tintColor=Color.Gray
                                    item?.let {
                                        if(item.collect){
                                            imageVector=Icons.Default.Favorite
                                            tintColor= C_Primary
                                        }
                                    }
                                    Icon(imageVector = imageVector, tint =tintColor, contentDescription = "",
                                        modifier=Modifier.constrainAs(bottomIcon){
                                            end.linkTo(parent.end)
                                            bottom.linkTo(parent.bottom)
                                        }.clickable {
                                            item?.let {
                                                collectItem=it
                                                if(item.collect){
                                                    squareViewModel.uncollectArticleById(item.id)
                                                }else{
                                                    squareViewModel.collectArticleById(item.id)
                                                }
                                            }
                                        })
                                }

                            }
                        }

                    }

                }
            }
        })

}