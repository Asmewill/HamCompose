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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.owapp.R
import com.example.owapp.route.RouteName
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.util.PagingStateUtil
import com.example.owapp.viewmodel.IndexViewModel
import com.example.owapp.widget.Banner
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mm.hamcompose.data.bean.BannerBean

/**
 * Created by Owen on 2023/5/19
 */
@Composable
fun IndexPage(navCtrl:NavHostController) {
    val indexViewModel: IndexViewModel = viewModel() //viewModel()可以保证viewModel只实例化一次
    if(!indexViewModel.isInit){//isInit标量标识是为了防止getHomeList()获取到数据之后，通过MutableLive通知的时候，循环调用getHomeList()
        indexViewModel.isInit=true
        indexViewModel.getBannerList()
        indexViewModel.getHomeList()
    }
   // var isRefresh by remember { indexViewModel.isRefreshing}
    val refreshState = rememberSwipeRefreshState(isRefreshing = true)
    //这里一定要对MutableLiveData进行状态监听，否则无法刷新页面
    val bannerListData =indexViewModel.bannerLiveData.observeAsState()
    val homeListData= indexViewModel.homeListLiveData.observeAsState().value?.collectAsLazyPagingItems()   //Flow<PagingData<Article>> 转换成  false

    SwipeRefresh(
        state = refreshState,
        onRefresh = {
            indexViewModel.getBannerList()
            indexViewModel.getHomeList()
     }){
        PagingStateUtil().pagingStateUtil(
            pagingData =homeListData!! ,
            refreshState =refreshState ,
            onErrorClick = {
                indexViewModel.getBannerList()
                indexViewModel.getHomeList()
            }) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ){
                if(bannerListData.value?.isNotEmpty()==true){
                    item {
                        Banner(list = bannerListData.value as MutableList<BannerBean>, onClick ={ url, title->
                            navCtrl.navigate(RouteName.WEBVIEW+"?url=${url}&title=${title}")
                        })
                    }
                }
                if(homeListData!=null&&homeListData.itemCount>0){
                  //  refreshState.isRefreshing=false
                    itemsIndexed(homeListData){index,item->
                        val author:String=if(TextUtils.isEmpty(item?.author))  item?.shareUser?:"" else  item?.author?:""
                        val authorIndex=if(author.isNotEmpty())author.substring(0,1) else ""
                        Card(
                            modifier = Modifier
                                .padding(vertical = 5.dp, horizontal = 10.dp)
                                .background(color = Color.White)
                                .clickable {
                                    //直接拼接传递参数
                                    navCtrl.navigate(RouteName.WEBVIEW + "?url=" + item?.link + "&title=" + item?.title)
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
                                    Text(text = authorIndex, fontSize = 12.sp, color = Color.White, modifier = Modifier.align(Alignment.Center))
                                }
                                //topText2
                                Text(text = author, fontSize = 14.sp, color = Color.Black,
                                    modifier = Modifier
                                        .padding(start = 6.dp)
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
                                Icon(imageVector = Icons.Default.FavoriteBorder, tint = Color.Gray, contentDescription = "",
                                    modifier=Modifier.constrainAs(bottomIcon){
                                        end.linkTo(parent.end)
                                        bottom.linkTo(parent.bottom)
                                    })
                            }
                        }
                    }
                }else{

                }
            }
        }
    }
}