package com.example.owapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.base.BaseViewModel
import com.example.owapp.http.HttpRepository
import com.example.owapp.http.HttpResult
import com.mm.hamcompose.data.bean.CollectBean
import com.mm.hamcompose.data.bean.ParentBean
import kotlinx.coroutines.flow.collectLatest


/**
 * Created by Owen on 2023/6/7
 */
class AddCollectViewModel:BaseViewModel() {
    var title = mutableStateOf("")
    val link = mutableStateOf("")
    val author= mutableStateOf("")
    val isSaved = mutableStateOf(false)


    /**
     * type: 0 = 添加新网站， 1 = 添加新文章 ， -1 = 编辑网站
     * id: 仅编辑网站时候用到此参数
     */
    val saveWebLiveData =MutableLiveData<ParentBean>()
    val isSaveArticle = mutableStateOf(false)
    val isSaveWebSite = mutableStateOf(false)
    fun saveNewCollect(type:Int,title:String,link:String,author:String="",id:Int=0){
        async {
            when(type){
                0->{ //添加新网站
                    HttpRepository.addNewWebsiteCollect(title,link).collectLatest {
                       when(it){
                           is HttpResult.Success->{
                               saveWebLiveData.value=it.result
                           }
                           is HttpResult.Error ->{
                               ToastUtils.showLong(it.exception.message)
                           }
                       }
                    }
                }
                1->{//添加新文章
                    HttpRepository.addNewArticleCollect(title,link,author).collectLatest {
                        when(it){
                            is HttpResult.Success->{
                                isSaveArticle.value=true //设置为true就会刷新页面
                            }
                            is HttpResult.Error ->{
                                ToastUtils.showLong(it.exception.message)
                            }
                        }
                    }
                }
                -1->{//编辑网站
                    HttpRepository.editCollectWebsite(id,title, link).collectLatest {
                        when(it){
                            is HttpResult.Success ->{
                                isSaveWebSite.value=true
                            }
                            is HttpResult.Error ->{
                                ToastUtils.showLong(it.exception.message)
                            }
                        }
                    }
                }
            }
        }
    }
}