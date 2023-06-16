package com.example.owapp.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.example.owapp.R
import com.example.owapp.ui.theme.C_Primary
import com.example.owapp.util.Constant
import com.example.owapp.util.HamToolBar2
import com.example.owapp.viewmodel.MyArticleViewModel
import com.mm.hamcompose.data.bean.UserInfo

/**
 * Created by Owen on 2023/5/25
 */
@Composable
fun MyArticlePage(navCtrl: NavHostController) {
    val mViewModel: MyArticleViewModel = viewModel()
    if (!mViewModel.isInit) {
        mViewModel.isInit = true
        mViewModel.getSharedArticleList()
    }
    var isEdit  by remember {
        mViewModel.isEdit
    }
    val shareList = mViewModel.shareLiveData.observeAsState().value
    val userInfo = GsonUtils.fromJson(
        SPUtils.getInstance().getString(Constant.LOGIN_BEAN),
        UserInfo::class.java
    )
    Column(Modifier.fillMaxSize()) {
        HamToolBar2(
            middleTitle = "${userInfo.username}",
            onBack = {
                navCtrl.navigateUp()
            },
            rightImageVetor = Icons.Default.Edit,
            onRightDrawableClick = {
                 isEdit=!isEdit
            }
        )
        shareList?.let {
            it.coinInfo.let { pointsBean ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(color = C_Primary),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1.0f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "等级", color = Color.White, fontSize = 14.sp)
                        Text(text = "${pointsBean.level}", color = Color.White, fontSize = 14.sp)
                    }
                    Column(
                        modifier = Modifier
                            .weight(1.0f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "积分",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                        Text(text = "${pointsBean.coinCount}", color = Color.White, fontSize = 14.sp)
                    }
                    Column(
                        modifier = Modifier
                            .weight(1.0f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "排行", color = Color.White, fontSize = 14.sp)
                        Text(text = "${pointsBean.rank}", color = Color.White, fontSize = 14.sp)
                    }
                }

            }
            Row(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth()
                    .height(38.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    Modifier
                        .width(5.dp)
                        .height(20.dp)
                        .background(color = Color.Black)
                )
                Text(
                    text = "文章列表",
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)) {
                shareList.shareArticles.datas?.let {
                    items(it) { itemBean ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(36.dp), verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${itemBean.title}",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.weight(1.0f))
                            if(isEdit){
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_close),
                                    contentDescription = "",
                                    tint = Color.Gray,
                                    modifier = Modifier.clickable {
                                        mViewModel.deleteArticle(itemBean.id)
                                    })
                            }
                        }
                    }
                }
            }
        }

    }
}