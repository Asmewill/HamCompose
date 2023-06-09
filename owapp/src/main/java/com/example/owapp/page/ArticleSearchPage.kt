package com.example.owapp.page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.R
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.ui.theme.ToolBarHeight
import com.google.accompanist.flowlayout.FlowRow

/**
 * Created by Owen on 2023/5/25
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArticleSearchPage(navCtrl: NavHostController) {
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
            BasicTextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(30.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                    .padding(start = 10.dp, top = 5.dp)  //padding在background之后的话，那就是内部padding
            )
            TextButton(modifier = Modifier
                .height(ToolBarHeight)
                .width(50.dp), onClick = {
                ToastUtils.showLong("搜索")
            }) {
                Text(text = "搜索", color = Color.White)
            }
        }

        LazyColumn() {
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
                    for (i in 1..10) {
                        Box(
                            modifier = Modifier
                                .padding(end = 6.dp, bottom = 6.dp)//这里的padding是margin
                                .height(28.dp)
                                .clip(shape = RoundedCornerShape(14.dp)) //先裁剪，在给背景颜色
                                .background(color = C_Primary).clickable {
                                  ToastUtils.showLong("面试")
                                 }.padding(//在Background之后的padding才是真正的padding
                                    start = 15.dp,
                                    end = 15.dp
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "面试", color = Color.White, fontSize = 14.sp)
                        }
                    }
                }
            }
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
                            ToastUtils.showLong("清空")
                        })
                }
            }
            items(5) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
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
                        text = "122",
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
                                ToastUtils.showLong("deleteed")
                            },
                        tint = Color.Gray)
                }
            }

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
            items(10){
                Card(modifier= Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(6.dp))
                ) {
                    Column(modifier = Modifier.padding(20.dp)){
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                            Box(modifier= Modifier
                                .size(20.dp)
                                .background(color = C_Primary, RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center){
                                Text(text = "水",color=Color.White, fontSize = 14.sp)
                            }
                            Text(text = "疯人堂",color=Color.Black, fontWeight = FontWeight.W500, fontSize = 15.sp,modifier=Modifier.padding(start = 5.dp))
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(painter = painterResource(id = R.drawable.ic_time), tint=Color.Gray,contentDescription ="",modifier=Modifier.size(15.dp))
                            Text(text = "2022-12-24",color=Color.Gray, fontSize = 14.sp)
                        }
                        Text(text = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                            maxLines = 2, overflow = TextOverflow.Ellipsis, color=Color.Gray, fontSize = 14.sp,modifier=Modifier.padding(vertical = 15.dp))
                        Row() {
                            Box(modifier= Modifier
                                .height(25.dp)
                                .background(color = C_Primary, RoundedCornerShape(12.5.dp)), contentAlignment = Alignment.Center){
                                Text(text = "广场Tab",color=Color.White, fontSize = 14.sp,modifier=Modifier.padding(horizontal = 10.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Box(modifier= Modifier
                                .height(25.dp)
                                .background(color = C_Primary, RoundedCornerShape(12.5.dp)), contentAlignment = Alignment.Center){
                                Text(text = "自助",color=Color.White, fontSize = 14.sp,modifier=Modifier.padding(horizontal = 10.dp))
                            }
                            Box(modifier=Modifier.fillMaxWidth()){
                                Icon(imageVector=Icons.Default.FavoriteBorder, tint = Color.Gray,contentDescription ="",modifier=Modifier.align(Alignment.CenterEnd))

                            }
                        }
                    }
                }
            }
        }
    }
}