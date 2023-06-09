package com.example.owapp.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.R
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.ui.theme.white4
import com.example.owapp.ui.theme.white5
import com.example.owapp.util.HamToolBar1
import com.example.owapp.viewmodel.ShareArticleViewModel

/**
 * Created by Owen on 2023/6/9
 */
@Composable
fun ShareArticlePage(navCtrl:NavHostController){
    val mViewModel:ShareArticleViewModel = viewModel()
    val titleValue by remember {
        mViewModel.title
    }
    val authorValue by remember {
        mViewModel.author
    }
    val urlValue by remember {
        mViewModel.link
    }
    val isShareSuccess  by remember{
       mViewModel.isShareSuccess
    }
    //分享文章的结果
    if(isShareSuccess){
        navCtrl.navigateUp()
    }
    Column(modifier=Modifier.fillMaxSize()) {
        HamToolBar1(middleTitle = "分享文章", onBack = {
               navCtrl.navigateUp()
        }, rightText="保存",onRightTextClick = {
           if(titleValue.isEmpty()){
                ToastUtils.showLong("请输入标题")
               return@HamToolBar1
           }
           if(authorValue.isEmpty()){
                ToastUtils.showLong("请输入作者名称")
               return@HamToolBar1
            }
            if(urlValue.isEmpty()){
                ToastUtils.showLong("请输入文章地址")
                return@HamToolBar1
            }
            mViewModel.shareArticle()
        })
    }

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
                textStyle = TextStyle(lineHeight = 0.sp, fontSize = 14.sp),
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
                    Text(text = "请输入标题", fontSize = 14.sp,color= white4)
                },
                colors= TextFieldDefaults.outlinedTextFieldColors(
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
                textStyle = TextStyle(lineHeight = 0.sp, fontSize = 14.sp),
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
                    Text(text = "请输入作者名称", fontSize = 14.sp,color= white4)
                },
                colors= TextFieldDefaults.outlinedTextFieldColors(
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
                textStyle = TextStyle(lineHeight = 0.sp, fontSize = 14.sp),
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
                    Text(text = "请输入文章地址", fontSize = 14.sp,color= white4)
                },
                colors= TextFieldDefaults.outlinedTextFieldColors(
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