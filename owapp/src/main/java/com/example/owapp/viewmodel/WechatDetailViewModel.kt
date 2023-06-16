package com.example.owapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.owapp.base.BaseViewModel
import com.example.owapp.http.HttpRepository
import com.mm.hamcompose.data.bean.Article
import kotlinx.coroutines.flow.Flow

/**
 * Created by Owen on 2023/6/14
 */
class WechatDetailViewModel:BaseViewModel() {

    val articleLiveData = MutableLiveData<Flow<PagingData<Article>>>()

    fun getWechatArticleList(publicId:Int){
        articleLiveData.value = HttpRepository.getPublicArticles(publicId).cachedIn(viewModelScope)
    }
}