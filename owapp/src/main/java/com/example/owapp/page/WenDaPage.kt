package com.example.owapp.page

import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.route.RouteName
import com.example.owapp.util.PagingStateUtil
import com.example.owapp.viewmodel.WenDaViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

/**
 * Created by Owen on 2023/5/19
 */
@Composable
fun WenDaPage(navCtrl: NavHostController) {
    val mContext = LocalContext.current
    val wenDaViewModel: WenDaViewModel = viewModel()
    if (!wenDaViewModel.isInit) {
        wenDaViewModel.getWendaList()
        wenDaViewModel.isInit = true
    }
    val wenDaList = wenDaViewModel.wenDaLiveData.observeAsState().value?.collectAsLazyPagingItems()
    val refreshState = rememberSwipeRefreshState(isRefreshing = false)

    SwipeRefresh(state = refreshState, onRefresh = {
        wenDaViewModel.getWendaList()
    }) {

        PagingStateUtil().pagingStateUtil(
            pagingData = wenDaList!!,
            refreshState = refreshState,
            onErrorClick = { wenDaViewModel.getWendaList() }) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (wenDaList != null && wenDaList.itemCount > 0) {
                    itemsIndexed(wenDaList) { index, item ->
                        Card(modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .background(Color.White)
                            .clickable {
                                navCtrl.navigate(RouteName.WEBVIEW + "?url=${item?.link}&title=${item?.title}")
                                wenDaViewModel.cacheHistory(item!!, mContext = mContext)
                            }) {
                            Column(
                                Modifier
                                    .padding(20.dp)
                                    .fillMaxSize()) {
                                Text(
                                    text = item?.title ?: "",
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Row(
                                    horizontalArrangement = Arrangement.Center, modifier = Modifier
                                        .padding(top = 8.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "作者:" + item?.author,
                                        color = Color.Gray,
                                        fontSize = 12.sp,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = item?.niceDate ?: "",
                                        color = Color.Gray,
                                        fontSize = 12.sp,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Text(
                                    text = Html.fromHtml(item?.desc ?: "").toString(),
                                    color = Color.Gray,
                                    fontSize = 15.sp,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

    }

}