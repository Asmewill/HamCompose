package com.example.owapp.page

import android.os.Bundle
import android.text.Html
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.owapp.route.RouteName
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.util.Constant
import com.example.owapp.util.HamToolBar2
import com.example.owapp.viewmodel.WechatDetailViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mm.hamcompose.data.bean.ParentBean
import com.mm.hamcompose.data.bean.WebData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Owen on 2023/6/13
 */
@Composable
fun WechatDetailPage(navCtrl:NavHostController,parentBean:ParentBean){
    val mViewModel:WechatDetailViewModel = viewModel()
    if(!mViewModel.isInit){
        mViewModel.isInit=true
        mViewModel.getWechatArticleList(parentBean.id)
    }
    val articleList=mViewModel.articleLiveData.observeAsState().value?.collectAsLazyPagingItems()


    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val refreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    Column(modifier=Modifier.fillMaxSize()) {
         HamToolBar2(middleTitle = "${parentBean.name}",rightImageVetor= Icons.Default.Search, onBack = {
             navCtrl.navigateUp()
         }, onRightDrawableClick = {
             navCtrl.currentBackStackEntry?.replaceArguments(Bundle().apply {
                 this.putParcelable(Constant.ARGS,parentBean)
             })
             navCtrl.navigate(RouteName.WECHAT_SEARCH){
                 launchSingleTop=true
                 restoreState=true
             }
         })
        SwipeRefresh(
            state = refreshState,
            onRefresh = {
                isRefreshing=true
                mViewModel.getWechatArticleList(parentBean.id)
            }){
            LazyColumn(){
                articleList?.apply {
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
                            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
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
                                    Box(modifier=Modifier.fillMaxWidth()){
                                        Icon(imageVector=Icons.Default.FavoriteBorder,
                                            tint = Color.Gray,contentDescription ="",modifier=Modifier.align(
                                                Alignment.CenterEnd))
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