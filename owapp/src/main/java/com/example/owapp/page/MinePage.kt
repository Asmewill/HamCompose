package com.example.owapp.page

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.R
import com.example.owapp.route.RouteName
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.ui.theme.white3
import com.example.owapp.util.Constant
import com.example.owapp.viewmodel.MineViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mm.hamcompose.data.bean.WebData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Owen on 2023/5/19
 */
@Composable
fun MinePage(navCtrl:NavHostController){
    val mineViewModel:MineViewModel = viewModel()
    val isLogin=SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)
    if(!mineViewModel.isInit&&isLogin){
        mineViewModel.isInit=true
        mineViewModel.getUserInfo()
    }
    val userInfo=mineViewModel.mineLiveData.observeAsState().value
    var isShowDialog by remember {
        mutableStateOf(false)
    }
    if(isShowDialog){
        Dialog(onDismissRequest = { isShowDialog=false}) {
            Card(shape= RoundedCornerShape(10.dp)) {
                Column(modifier= Modifier
                    .fillMaxWidth(0.95f)
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text(text = "加入我们",color=Color.Black, fontSize = 16.sp)
                        Text(text = "开源作者:鸿洋",color=Color.Gray,fontSize = 14.sp,modifier=Modifier.padding(top = 10.dp))
                        Text(text = "作者博客:",color=Color.Gray,fontSize = 14.sp,modifier=Modifier.padding(top = 10.dp))
                        Text(text = "Https://blog.csdn.net/lmj623565791",color=Color.Gray,fontSize = 14.sp)
                        Text(text = "QQ交流群：591683946",color=Color.Gray,fontSize = 14.sp,modifier=Modifier.padding(top = 10.dp))
                    }
                    Text(text = "确定",modifier= Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                        .clickable {
                            isShowDialog = false
                        }
                        .padding(top = 14.dp), textAlign = TextAlign.Center)
                }
            }
        }
    }
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false)}
    val refreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    SwipeRefresh(
        state = refreshState,
        onRefresh = {
            isRefreshing=true
            if(isLogin){
                mineViewModel.getUserInfo()
            }else{
                ToastUtils.showLong("请先登录")
            }
        }){
        LazyColumn(){
            item {
                Column(modifier= Modifier
                    .fillMaxSize()
                    .background(color = C_Primary)) {
                    Row(modifier= Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 50.dp)
                        .height(80.dp)
                        .fillMaxWidth()
                        .clickable {
                            if (!SPUtils
                                    .getInstance()
                                    .getBoolean(Constant.IS_LOGIN)
                            ) {
                                navCtrl.navigate(RouteName.LOGIN)
                            }
                        }) {
                        Surface(shape = CircleShape, modifier = Modifier.size(80.dp)) {
                            Image(painter = painterResource(id = R.mipmap.ic_account), contentDescription = "")
                        }
                        Column(modifier= Modifier
                            .padding(start = 20.dp)
                            .height(80.dp), verticalArrangement = Arrangement.Center) {
                            if(isLogin&&userInfo!=null){
                                scope.launch {
                                    delay(500)
                                    isRefreshing=false
                                }
                                Text(text = userInfo.userInfo.username, fontSize = 18.sp, modifier = Modifier.padding(bottom = 10.dp))
                                Row() {
                                    Text(text = "id:${userInfo.userInfo.id}", fontSize = 12.sp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(text = "排名:${userInfo.coinInfo.rank}", fontSize = 12.sp)
                                }
                            }else{
                                Text(text = "请先登录~", fontSize = 18.sp, modifier = Modifier.padding(bottom = 10.dp))
                                Row() {
                                    Text(text = "id:----", fontSize = 12.sp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(text = "排名:----", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                    Card(
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                        backgroundColor = Color.White,
                        modifier = Modifier
                            .padding(top = 50.dp)
                            .fillMaxSize()
                    ) {
                        Column(modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxSize()) {
                            Row(modifier= Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clickable {
                                    if (isLogin) {
                                        navCtrl.navigate(RouteName.POINTRANKING)
                                    } else {
                                        navCtrl.navigate(RouteName.LOGIN)
                                    }
                                }, verticalAlignment = Alignment.CenterVertically) {
                                Spacer(modifier = Modifier.width(20.dp))
                                Icon(painter = painterResource(id = R.mipmap.ic_jifen), contentDescription ="",modifier=Modifier.size(25.dp), tint = C_Primary)
                                Text(text = "我的积分",modifier=Modifier.padding(start = 15.dp),color=Color.Black)
                                Spacer(Modifier.weight(1f))
                                Icon(painter = painterResource(id = R.mipmap.ic_right), contentDescription ="",modifier=Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(20.dp))
                            }
                            Row(modifier= Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clickable {
                                    if (isLogin) {
                                        navCtrl.navigate(RouteName.MY_COLLECT)
                                    } else {
                                        navCtrl.navigate(RouteName.LOGIN)
                                    }
                                }, verticalAlignment = Alignment.CenterVertically) {
                                Spacer(modifier = Modifier.width(20.dp))
                                Icon(painter = painterResource(id = R.mipmap.ic_collect), contentDescription ="",modifier=Modifier.size(25.dp), tint = C_Primary)
                                Text(text = "我的收藏",modifier=Modifier.padding(start = 15.dp),color=Color.Black)
                                Spacer(Modifier.weight(1f))
                                Icon(painter = painterResource(id = R.mipmap.ic_right), contentDescription ="",modifier=Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(20.dp))
                            }
                            Row(modifier= Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clickable {
                                    if (isLogin) {
                                        navCtrl.navigate(RouteName.MY_ARTICLE)
                                    } else {
                                        navCtrl.navigate(RouteName.LOGIN)
                                    }
                                }, verticalAlignment = Alignment.CenterVertically) {
                                Spacer(modifier = Modifier.width(20.dp))
                                Icon(painter = painterResource(id = R.mipmap.ic_wenzhang), contentDescription ="",modifier=Modifier.size(25.dp), tint = C_Primary)
                                Text(text = "我分享的文章",modifier=Modifier.padding(start = 15.dp),color=Color.Black)
                                Spacer(Modifier.weight(1f))
                                Icon(painter = painterResource(id = R.mipmap.ic_right), contentDescription ="",modifier=Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(20.dp))
                            }
                            Row(modifier= Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clickable {
                                    navCtrl.navigate(RouteName.HISTORY_RECORD)
                                }, verticalAlignment = Alignment.CenterVertically) {
                                Spacer(modifier = Modifier.width(20.dp))
                                Icon(painter = painterResource(id = R.mipmap.ic_wenzhang), contentDescription ="",modifier=Modifier.size(25.dp), tint = C_Primary)
                                Text(text = "浏览记录",modifier=Modifier.padding(start = 15.dp),color=Color.Black)
                                Spacer(Modifier.weight(1f))
                                Icon(painter = painterResource(id = R.mipmap.ic_right), contentDescription ="",modifier=Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(20.dp))
                            }
                            Row(modifier= Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clickable {
                                    if (isLogin) {
                                        val bundle = Bundle()
                                        bundle.putParcelable(
                                            Constant.ARGS,
                                            WebData("官方网站", "https://www.wanandroid.com/index")
                                        )
                                        navCtrl.currentBackStackEntry?.replaceArguments(bundle)
                                        navCtrl.navigate(RouteName.WEBVIEW) {
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    } else {
                                        navCtrl.navigate(RouteName.LOGIN)
                                    }
                                }, verticalAlignment = Alignment.CenterVertically) {
                                Spacer(modifier = Modifier.width(20.dp))
                                Icon(painter = painterResource(id = R.mipmap.ic_web), contentDescription ="",modifier=Modifier.size(25.dp), tint = C_Primary)
                                Text(text = "开源网站",modifier=Modifier.padding(start = 15.dp),color=Color.Black)
                                Spacer(Modifier.weight(1f))
                                Icon(painter = painterResource(id = R.mipmap.ic_right), contentDescription ="",modifier=Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(20.dp))
                            }
                            Row(modifier= Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clickable {
                                    isShowDialog = true
                                }, verticalAlignment = Alignment.CenterVertically) {
                                Spacer(modifier = Modifier.width(20.dp))
                                Icon(painter = painterResource(id = R.mipmap.ic_jairu), contentDescription ="",modifier=Modifier.size(25.dp), tint = C_Primary)
                                Text(text = "加入我们",modifier=Modifier.padding(start = 15.dp),color=Color.Black)
                                Spacer(Modifier.weight(1f))
                                Icon(painter = painterResource(id = R.mipmap.ic_right), contentDescription ="",modifier=Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(20.dp))
                            }
                            Row(modifier= Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clickable {
                                    if (isLogin) {
                                        navCtrl.navigate(RouteName.SETTINGS)
                                    } else {
                                        navCtrl.navigate(RouteName.LOGIN)
                                    }
                                }, verticalAlignment = Alignment.CenterVertically) {
                                Spacer(modifier = Modifier.width(20.dp))
                                Icon(painter = painterResource(id = R.mipmap.ic_shezhi), contentDescription ="",modifier=Modifier.size(25.dp), tint = C_Primary)
                                Text(text = "系统设置",modifier=Modifier.padding(start = 15.dp),color=Color.Black)
                                Spacer(Modifier.weight(1f))
                                Icon(painter = painterResource(id = R.mipmap.ic_right), contentDescription ="",modifier=Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(20.dp))
                            }

                            Row(modifier= Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clickable {
                                    navCtrl.navigate(RouteName.STUDY)
                                }, verticalAlignment = Alignment.CenterVertically) {
                                Spacer(modifier = Modifier.width(20.dp))
                                Icon(painter = painterResource(id = R.mipmap.ic_wenzhang), contentDescription ="",modifier=Modifier.size(25.dp), tint = C_Primary)
                                Text(text = "学习",modifier=Modifier.padding(start = 15.dp),color=Color.Black)
                                Spacer(Modifier.weight(1f))
                                Icon(painter = painterResource(id = R.mipmap.ic_right), contentDescription ="",modifier=Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(20.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}