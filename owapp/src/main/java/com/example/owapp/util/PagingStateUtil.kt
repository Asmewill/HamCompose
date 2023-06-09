package com.example.owapp.util

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.owapp.widget.ErrorComposable
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.mm.hamcompose.data.bean.Article

/**
 * Created by Owen on 2023/6/1
 */
class PagingStateUtil {
   @Composable
    fun <T:Any> pagingStateUtil(
       pagingData: LazyPagingItems<T>,
       refreshState:SwipeRefreshState,
       onErrorClick: ()->Unit,
       content: @Composable ()->Unit){
        when(pagingData.loadState.refresh){
            is LoadState.NotLoading ->{
                refreshState.isRefreshing=false
                content()
            }
            is LoadState.Error->{
                ErrorComposable("出错了，请点击重试"){
                    onErrorClick.invoke()
                }
                refreshState.isRefreshing=false
            }
            is LoadState.Loading->{
                Row(modifier = Modifier.fillMaxSize()) { }
                //显示刷新头
                if (!refreshState.isRefreshing) refreshState.isRefreshing = true
            }
        }
    }

}