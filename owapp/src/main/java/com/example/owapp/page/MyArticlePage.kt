package com.example.owapp.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.R
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.util.HamToolBar1
import com.example.owapp.util.HamToolBar2

/**
 * Created by Owen on 2023/5/25
 */
@Composable
fun MyArticlePage(navCtrl:NavHostController) {
    Column(Modifier.fillMaxSize()) {
        HamToolBar2(
            middleTitle = "d**est0002",
            onBack = {
                navCtrl.navigateUp()
            },
            rightImageVetor = Icons.Default.Edit,
            onRightDrawableClick = {
                 ToastUtils.showLong("编辑")
            }
        )
        Row(modifier= Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = C_Primary),
          ){
            Column(modifier = Modifier
                .weight(1.0f)
                .fillMaxHeight(),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "等级",color= Color.White, fontSize = 14.sp)
                Text(text = "2",color= Color.White, fontSize = 14.sp)

            }
            Column(modifier = Modifier
                .weight(1.0f)
                .fillMaxHeight(),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "积分",color= Color.White, fontSize = 14.sp)
                Text(text = "2",color= Color.White, fontSize = 14.sp)
            }
            Column(modifier = Modifier
                .weight(1.0f)
                .fillMaxHeight(),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "排行",color= Color.White, fontSize = 14.sp)
                Text(text = "2",color= Color.White, fontSize = 14.sp)
            }
        }
        Row(modifier= Modifier
            .padding(start = 10.dp)
            .fillMaxWidth()
            .height(38.dp), verticalAlignment = Alignment.CenterVertically){
            Spacer(
                Modifier
                    .width(5.dp)
                    .height(20.dp).background(color=Color.Black))
            Text(text = "文章列表",color=Color.Black, fontSize = 16.sp, modifier = Modifier.padding(start = 10.dp))
        }
        LazyColumn(modifier=Modifier.padding(horizontal = 10.dp)){
            items(10){
                Row(modifier=Modifier.fillMaxWidth().height(36.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "1.吃鸡游戏攻略", fontSize = 14.sp,color=Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Spacer(modifier = Modifier.weight(1.0f))
                    Icon(painter = painterResource(id = R.drawable.ic_close), contentDescription ="",tint=Color.Gray, modifier = Modifier.clickable {
                        ToastUtils.showLong("删除")
                    })
                }
            }
        }
    }
}