package com.example.owapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.owapp.base.BaseViewModel
import com.example.owapp.http.HttpRepository
import com.example.owapp.http.HttpResult
import com.mm.hamcompose.data.bean.Article
import com.mm.hamcompose.data.bean.BannerBean
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Owen on 2023/5/30
 */
class IndexViewModel:BaseViewModel() {
     var homeListLiveData = MutableLiveData<Flow<PagingData<Article>>>()
     var bannerLiveData = MutableLiveData<MutableList<BannerBean>>(null)
     val isRefreshing = mutableStateOf(false)
     fun getHomeList(){
          val tempList= HttpRepository.getIndexData().cachedIn(viewModelScope)
          homeListLiveData.value=tempList
     }
     fun getBannerList(){
          bannerLiveData.value=null
          async {
               HttpRepository.getBanners().collectLatest {
                    when(it){
                         is HttpResult.Success->{
                              bannerLiveData.value=it.result
                         }
                         is HttpResult.Error->{
                              bannerLiveData.value?.clear()
                         }
                    }
               }
          }
     }
     //直接使用原始HttpResult数据
     val  topListBean= mutableStateOf<HttpResult<MutableList<Article>>?>(null)
     fun getTopArticleList(){
          async {
               HttpRepository.getTopArticles().collectLatest {
                    topListBean.value=it
               }
          }
     }
}