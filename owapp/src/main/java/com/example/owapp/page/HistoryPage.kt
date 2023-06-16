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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.route.RouteName
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.util.HamToolBar1
import com.example.owapp.viewmodel.HistoryViewModel

/**
 * Created by Owen on 2023/6/16
 */
@Composable
fun HistoryPage(navCtrl:NavHostController) {
    val mContext= LocalContext.current
    val mViewModel: HistoryViewModel = viewModel()
    if(!mViewModel.isInit){
        mViewModel.getHistoryList(mContext)
    }
    val historyList  by remember {
        mViewModel.historyListState
    }

    Column(modifier=Modifier.fillMaxSize()) {
        HamToolBar1(middleTitle = "浏览记录", onBack ={
            navCtrl.navigateUp()
        }, rightText = "清除所有" , onRightTextClick = {
            mViewModel.clearAllHistory(mContext = mContext)
        })
        LazyColumn{

            historyList?.let {
                it.reverse()
                it.forEachIndexed{ index,item->
                    item{
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
                                    .background(
                                        color = C_Primary,
                                        shape = RoundedCornerShape(12.5.dp)
                                    )
                                    .constrainAs(topText1) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                    }){
                                    Text(text = authorIndex, fontSize = 12.sp, color = Color.White, modifier = Modifier.align(
                                        Alignment.Center))
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
                                //topText4
                                Text(text = item?.niceDate?:"", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.constrainAs(topText4){
                                    top.linkTo(parent.top)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(topText1.bottom)
                                })
                                //topIcon
                                Icon(painter = painterResource(id = com.example.owapp.R.drawable.ic_time), tint = Color.Gray,
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
                                    .background(
                                        color = C_Primary,
                                        shape = RoundedCornerShape(12.5.dp)
                                    )
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
                                    .background(
                                        color = C_Primary,
                                        shape = RoundedCornerShape(12.5.dp)
                                    )
                                    .constrainAs(tab2) {
                                        bottom.linkTo(parent.bottom)
                                        start.linkTo(tab1.end)
                                        top.linkTo(middleText.bottom)
                                    }){
                                    Text(text = item?.chapterName?:"", fontSize = 12.sp, color = Color.White, modifier = Modifier
                                        .padding(start = 12.dp, end = 12.dp)
                                        .align(Alignment.Center))
                                }
                            }
                        }

                    }
                }
            }

        }
    }


}