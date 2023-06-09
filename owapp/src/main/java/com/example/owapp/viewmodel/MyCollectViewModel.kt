package com.example.owapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.base.BaseViewModel
import com.example.owapp.http.HttpRepository
import com.example.owapp.http.HttpResult
import com.mm.hamcompose.data.bean.CollectBean
import com.mm.hamcompose.data.bean.ParentBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Owen on 2023/6/7
 */
class MyCollectViewModel :BaseViewModel(){
    val  urlListLiveData = MutableLiveData<MutableList<ParentBean>>()
     fun getCollectUrlList(){
         async {
             HttpRepository.getCollectUrls().collectLatest {
                when(it){
                    is HttpResult.Success ->{
                        urlListLiveData.value=it.result
                    }
                    is HttpResult.Error ->{
                        viewModelScope.launch(Dispatchers.Main) {
                            ToastUtils.showLong("加载出错，请重试!!!")
                        }
                    }
                }
             }
         }
     }
    val articleLiveData= MutableLiveData<Flow<PagingData<CollectBean>>>()
    fun getArticleList(){
        articleLiveData.value=HttpRepository.getCollectionList().cachedIn(viewModelScope)
    }


    fun unCollectArticle(id:Int ,originId:Int){
        async {
            HttpRepository.uncollectArticleById(id, originId).collectLatest {
                when(it){
                    is HttpResult.Success->{

                    }
                    is HttpResult.Error ->{
                        it.exception.message?.let {
                            if(it.equals("504")){ // 取消收藏成功
                                getArticleList() //从新刷新文章列表
                            }
                        }
                    }
                }
            }
        }
    }

    fun deleteWebsite(id:Int){
        async {
            HttpRepository.deleteWebsite(id).collectLatest {
                when(it){
                    is HttpResult.Success ->{

                    }
                    is HttpResult.Error ->{
                        if(it.exception.message.equals("504")){
                            ToastUtils.showLong("删除成功")
                            getCollectUrlList()
                        }
                    }
                }
            }
        }
    }
}