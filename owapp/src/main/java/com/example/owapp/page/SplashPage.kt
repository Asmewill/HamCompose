package com.example.owapp.page

import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.owapp.R
import com.example.owapp.ui.theme.splashText

/**
 * Created by Owen on 2023/5/18
 */
@Composable
fun SplashPage(onNextPage:()->Unit){

    val imageRes = listOf(
        R.mipmap.splash_image01,
        R.mipmap.splash_image02,
        R.mipmap.splash_image03,
        R.mipmap.splash_image04,
        R.mipmap.splash_image05
    )

    var internal by remember { mutableStateOf(4)}
    val myCounter=object:CountDownTimer(4000,1000){
        override fun onTick(p0: Long) {
            if(internal>=0){
                internal-=1
            }
        }
        override fun onFinish() {
            onNextPage()
        }
    }
    myCounter.start()
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()){
        Image(painter = painterResource(id = imageRes[0]), modifier = Modifier.fillMaxHeight().fillMaxWidth(),contentDescription="", contentScale = ContentScale.FillBounds)
        val (tips,wanText,androidText,version)=createRefs()
        //TipsView
        Box(contentAlignment = Alignment.Center,
            //有宽高一定要先padding，才是margin,
            modifier = Modifier.padding(top=20.dp,end = 20.dp).width(60.dp).height(30.dp).background(splashText,shape= RoundedCornerShape(15.dp))
                .constrainAs(tips){
            top.linkTo(parent.top)
            end.linkTo(parent.end)}.clickable {
                onNextPage()
            }){
            Text(text ="$internal",color=Color.White,fontSize=12.sp)
        }
        Text(text="Wan", fontSize = 28.sp,color=Color.White,
            modifier = Modifier.padding(bottom = 30.dp, end = 90.dp).constrainAs(wanText){
                 top.linkTo(parent.top)
                 bottom.linkTo(parent.bottom)
                 start.linkTo(parent.start)
                 end.linkTo(parent.end)
        })
        Text(text="Android", fontSize = 30.sp,color=Color.White,
            modifier = Modifier.padding(top = 30.dp, start = 90.dp).constrainAs(androidText){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        Text(text="Version:1.0.0", fontSize = 14.sp,color=Color.White,
            modifier = Modifier.padding(bottom = 40.dp).constrainAs(version){
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
    }
}
