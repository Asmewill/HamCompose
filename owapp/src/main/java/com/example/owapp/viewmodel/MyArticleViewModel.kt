package com.example.owapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.base.BaseViewModel
import com.example.owapp.http.HttpRepository
import com.example.owapp.http.HttpResult
import com.example.owapp.util.Constant
import com.mm.hamcompose.data.bean.Article
import com.mm.hamcompose.data.bean.SharerBean
import com.mm.hamcompose.data.bean.UserInfo
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Owen on 2023/6/12
 */
class MyArticleViewModel:BaseViewModel() {
    val isEdit= mutableStateOf(false)
    val page= mutableStateOf(1)
    val shareLiveData = MutableLiveData<SharerBean<Article>>()

    fun getSharedArticleList(){
        async {
            HttpRepository.getMyShareArticles(page.value).collectLatest {
                when(it){
                    is HttpResult.Success->{
                        shareLiveData.value=it.result
                    }
                    is HttpResult.Error->{
                        it.exception.message?.let { emsg->
                            ToastUtils.showLong(emsg)
                        }
                    }
                }
            }
        }
    }

    fun deleteArticle(id:Int){
        async {
            HttpRepository.deleteMyShareArticle(id).collectLatest {
                when(it){
                    is HttpResult.Success->{

                    }
                    is HttpResult.Error->{
                        if(it.exception.message.equals("504")){
                            ToastUtils.showLong("删除成功!!!")
                            getSharedArticleList()
                        }
                    }
                }
            }
        }
    }
}