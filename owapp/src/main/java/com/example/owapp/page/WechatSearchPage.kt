package com.example.owapp.page

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FavoriteBorder
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
import com.example.owapp.ui.theme.ToolBarHeight
import com.example.owapp.util.Constant
import com.example.owapp.util.items
import com.example.owapp.viewmodel.WechatSearchViewModel
import com.example.owapp.widget.EmptyView
import com.mm.hamcompose.data.bean.ParentBean
import com.mm.hamcompose.data.bean.WebData

/**
 * Created by Owen on 2023/6/13
 */
@Composable
fun WechatSearchPage(navCtrl: NavHostController, parentBean:ParentBean) {
    val mViewModel :WechatSearchViewModel = viewModel()
    val searchList=mViewModel.articleLiveData.observeAsState().value?.collectAsLazyPagingItems()
    var inputText by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize()) {
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
            Box(
                modifier = Modifier
                    .weight(1.0f)
                    .height(30.dp)
            ) {
                BasicTextField(
                    singleLine = true,
                    value = inputText,
                    onValueChange = {
                        inputText = it
                        mViewModel.searchArticleWithKey(parentBean.id,inputText)
                    },
                    decorationBox = { innerTextField ->
                        BoxWithConstraints {
                            Box() {
                                innerTextField()
                                if (inputText.isEmpty()) {
                                    Text(
                                        text = "请输入想要搜索的内容",
                                        fontSize = 12.sp,
                                        color = Color.Gray,
                                        modifier = Modifier.padding(start = 0.dp, top = 2.dp)
                                    )
                                } else {
                                    Text(
                                        text = "",
                                        fontSize = 12.sp,
                                        color = Color.Gray,
                                        modifier = Modifier.padding(start = 0.dp, top = 2.dp)
                                    )
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                        .padding(
                            start = 10.dp,
                            top = 5.dp,
                            end = 30.dp
                        )  //padding在background之后的话，那就是内部padding
                )
                if (!TextUtils.isEmpty(inputText)) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .align(Alignment.CenterEnd)
                            .size(15.dp)
                            .clickable {
                                inputText = ""
                            }, tint = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
        //MainContent
        if(inputText.isNotEmpty()){
            LazyColumn(){
                searchList?.apply {
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
        }else{
            EmptyView()
        }
    }
}