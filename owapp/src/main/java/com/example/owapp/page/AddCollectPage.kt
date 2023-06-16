package com.example.owapp.page

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.R
import com.example.owapp.event.Event
import com.example.owapp.event.PostBean
import com.example.owapp.ui.theme.*
import com.example.owapp.util.HamToolBar1
import com.example.owapp.viewmodel.AddCollectViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import java.time.format.TextStyle

/**
 * Created by Owen on 2023/5/29
 * 添加收藏
 */
@SuppressLint("RememberReturnType")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun AddCollectPage(navCtrl:NavHostController) {
    val addCollectViewModel:AddCollectViewModel = viewModel()
    val pageState = rememberPagerState(pageCount = 2,initialPage=0,initialOffscreenLimit=1)
    var selectIndex  by remember { mutableStateOf(0)}
    val scope = rememberCoroutineScope()
    var parentBean= addCollectViewModel.saveWebLiveData.observeAsState().value
    var isSavedArticle  by remember { addCollectViewModel.isSaveArticle}
    val titleValue by remember {
       addCollectViewModel.title
    }
    val urlValue by remember {
        addCollectViewModel.link
    }
    val authorValue by remember{
        addCollectViewModel.author
    }
    //保存网址成功后回调
    parentBean?.let {
        Event.messgeEvent.value.type=1   //通知MyCollectPage 页面刷新网址
        Event.messgeEvent.value.obj=PostBean("title1","content1") //测试EeventBus携带数据
        addCollectViewModel.saveWebLiveData.value=null  //防止连续刷新页面的时候，连续调用二次 navCtrl.navigateUp()
        navCtrl.navigateUp()
    }
    //保存文章成功后回调
    if(isSavedArticle){
        Event.messgeEvent.value.type=2
        Event.messgeEvent.value.obj=PostBean("title2","content2") //测试EeventBus携带数据
        addCollectViewModel.isSaveArticle.value=false
        navCtrl.navigateUp()
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxSize()
        .background(color = C_Primary)) {
        HamToolBar1(middleTitle = "添加收藏", onBack = {
            navCtrl.navigateUp()
        }, rightText = "保存", onRightTextClick = {
            when(selectIndex){
                0->{
                    if(TextUtils.isEmpty(titleValue)){
                        ToastUtils.showLong("标题不能为空!!!")
                        return@HamToolBar1
                    }
                    if(TextUtils.isEmpty(urlValue)){
                        ToastUtils.showLong("网址不能为空!!!")
                        return@HamToolBar1
                    }
                    addCollectViewModel.saveNewCollect(0,titleValue,urlValue)
                }
                1->{
                    if(TextUtils.isEmpty(titleValue)){
                        ToastUtils.showLong("标题不能为空!!!")
                        return@HamToolBar1
                    }
                    if(TextUtils.isEmpty(authorValue)){
                        ToastUtils.showLong("作者不能为空!!!")
                        return@HamToolBar1
                    }

                    if(TextUtils.isEmpty(urlValue)){
                        ToastUtils.showLong("文章地址不能为空!!!")
                        return@HamToolBar1
                    }
                    addCollectViewModel.saveNewCollect(1,titleValue,urlValue,authorValue)
                }
            }
        })
        Box(modifier= Modifier
            .fillMaxWidth(0.35f)
            .height(60.dp)
            .background(color = C_Primary)) {
            Text(text="网站", fontSize = 16.sp,color= if(selectIndex==0 )Color.Black else Color.Gray,modifier= Modifier
                .align(Alignment.CenterStart)
                .clickable {
                    scope.launch {
                        pageState.scrollToPage(0)
                    }
                })
            Spacer(modifier= Modifier
                .width(1.dp)
                .height(18.dp)
                .background(color = Color.Gray)
                .align(Alignment.Center))
            Text(text="文章", fontSize = 16.sp,color= if(selectIndex==1)Color.Black else Color.Gray,modifier= Modifier
                .align(Alignment.CenterEnd)
                .clickable {
                    scope.launch {
                        pageState.scrollToPage(1)
                    }
                })
        }

        Column(
            Modifier
                .fillMaxSize()
                .background(color = white1)) {
               HorizontalPager(state = pageState) {page->
                   selectIndex=pageState.currentPage
                   when(page){
                      0->{
                          TabAddWebSite(titleValue,urlValue,addCollectViewModel)
                      }
                      1->{
                          TabAddArticle(titleValue,urlValue,authorValue,addCollectViewModel)
                      }
                   }
               }
        }
    }
}
@Composable
fun  TabAddWebSite(titleValue:String, urlValue:String,viewModel:AddCollectViewModel){
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 25.dp, end = 25.dp)) {
        Row(modifier=Modifier.padding(top=25.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "标题", fontSize = 14.sp)
            //TextField必须是>=50dp,否则容易导致输入文字无法正常显示
            TextField(
                modifier= Modifier
                    .fillMaxWidth().height(50.dp).clip(shape = RoundedCornerShape(25.dp)).background(color=Color.White)
                ,
                value =titleValue , onValueChange ={
                    viewModel.title.value=it
                },
                textStyle = androidx.compose.ui.text.TextStyle(lineHeight = 0.sp, fontSize = 14.sp),
                trailingIcon = {
                    if(!titleValue.isEmpty()){
                        Icon(painter = painterResource(id = R.drawable.ic_close),
                            contentDescription ="",
                            modifier= Modifier
                                .size(20.dp)
                                .offset(x = 15.dp)
                                .clickable {
                                    viewModel.title.value = ""
                                })
                    }
                },
                placeholder = {
                    Text(text = "请输入标题", fontSize = 14.sp,color=white4)
                },
                colors=TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Transparent,//边框未获取焦点
                    focusedBorderColor = Color.Transparent,//边框获取焦点
                    // placeholderColor = white4 //hitText文字颜色
                    textColor = C_Primary , //文字颜色
                    cursorColor = C_Primary //光标颜色
                )
            )
        }
        Divider(color= white5)

        Row(modifier=Modifier.padding(top=25.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "网址", fontSize = 14.sp)
            TextField(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                value =urlValue , onValueChange ={
                    viewModel.link.value=it
                },
                textStyle = androidx.compose.ui.text.TextStyle(lineHeight = 0.sp, fontSize = 14.sp),
                trailingIcon = {
                    if(!urlValue.isEmpty()){
                        Icon(painter = painterResource(id = R.drawable.ic_close),
                            contentDescription ="",
                            modifier= Modifier
                                .size(20.dp)
                                .offset(x = 15.dp)
                                .clickable {
                                    viewModel.link.value = ""
                                })
                    }
                },
                placeholder = {
                    Text(text = "请输入网址", fontSize = 14.sp,color=white4)
                },
                colors=TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Transparent,//边框未获取焦点
                    focusedBorderColor = Color.Transparent,//边框获取焦点
                    // placeholderColor = white4 //hitText文字颜色
                    textColor = C_Primary , //文字颜色
                    cursorColor = C_Primary //光标颜色
                )
            )
        }
        Divider(color= white5)
    }
}

@Composable
fun  TabAddArticle(titleValue: String,urlValue: String,authorValue:String,mViewModel:AddCollectViewModel){
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 25.dp, end = 25.dp)) {
        Row(modifier=Modifier.padding(top=25.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "标题", fontSize = 14.sp)
            TextField(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                value =titleValue , onValueChange ={
                    mViewModel.title.value =it
                },
                textStyle = androidx.compose.ui.text.TextStyle(lineHeight = 0.sp, fontSize = 14.sp),
                trailingIcon = {
                    if(!titleValue.isEmpty()){
                        Icon(painter = painterResource(id = R.drawable.ic_close),
                            contentDescription ="",
                            modifier= Modifier
                                .size(20.dp)
                                .offset(x = 15.dp)
                                .clickable {
                                    mViewModel.title.value = ""
                                })
                    }
                },
                placeholder = {
                    Text(text = "请输入标题", fontSize = 14.sp,color=white4)
                },
                colors=TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Transparent,//边框未获取焦点
                    focusedBorderColor = Color.Transparent,//边框获取焦点
                    // placeholderColor = white4 //hitText文字颜色
                    textColor = C_Primary , //文字颜色
                    cursorColor = C_Primary //光标颜色
                )
            )
        }
        Divider(color= white5)

        Row(modifier=Modifier.padding(top=25.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "作者", fontSize = 14.sp)
            TextField(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                value =authorValue , onValueChange ={
                    mViewModel.author.value=it
                },
                textStyle = androidx.compose.ui.text.TextStyle(lineHeight = 0.sp, fontSize = 14.sp),
                trailingIcon = {
                    if(!authorValue.isEmpty()){
                        Icon(painter = painterResource(id = R.drawable.ic_close),
                            contentDescription ="",
                            modifier= Modifier
                                .size(20.dp)
                                .offset(x = 15.dp)
                                .clickable {
                                    mViewModel.author.value = ""
                                })
                    }
                },
                placeholder = {
                    Text(text = "请输入作者名称", fontSize = 14.sp,color=white4)
                },
                colors=TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Transparent,//边框未获取焦点
                    focusedBorderColor = Color.Transparent,//边框获取焦点
                    // placeholderColor = white4 //hitText文字颜色
                    textColor = C_Primary , //文字颜色
                    cursorColor = C_Primary //光标颜色
                )
            )
        }
        Divider(color= white5)
        Row(modifier=Modifier.padding(top=25.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "文章地址", fontSize = 14.sp)
            TextField(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                value =urlValue , onValueChange ={
                    mViewModel.link.value=it
                },
                textStyle = androidx.compose.ui.text.TextStyle(lineHeight = 0.sp, fontSize = 14.sp),
                trailingIcon = {
                    if(!urlValue.isEmpty()){
                        Icon(painter = painterResource(id = R.drawable.ic_close),
                            contentDescription ="",
                            modifier= Modifier
                                .size(20.dp)
                                .offset(x = 15.dp)
                                .clickable {
                                    mViewModel.link.value = ""
                                })
                    }
                },
                placeholder = {
                    Text(text = "请输入文章地址", fontSize = 14.sp,color=white4)
                },
                colors=TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Transparent,//边框未获取焦点
                    focusedBorderColor = Color.Transparent,//边框获取焦点
                    // placeholderColor = white4 //hitText文字颜色
                    textColor = C_Primary , //文字颜色
                    cursorColor = C_Primary //光标颜色
                )
            )
        }
        Divider(color= white5)
    }
}

