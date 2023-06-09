package com.example.owapp.viewmodel

import android.text.TextUtils
import androidx.compose.runtime.mutableStateOf
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.base.BaseViewModel
import com.example.owapp.http.HttpRepository
import com.example.owapp.http.HttpResult
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Owen on 2023/6/9
 */
class ShareArticleViewModel:BaseViewModel() {
    var title= mutableStateOf("")
    val author = mutableStateOf("")
    val link = mutableStateOf("")
    var isShareSuccess = mutableStateOf(false)
    fun shareArticle(){
        async {
            HttpRepository
                .addMyShareArticle(title.value,link.value,author.value)
                .collectLatest {
                    when(it){
                        is HttpResult.Success ->{
                        }
                        is HttpResult.Error ->{
                            if(it.exception.message.equals("504")){
                                 isShareSuccess.value=true
                                ToastUtils.showLong("分享成功")
                            }else{
                                it.exception.message?.let { msg->
                                    ToastUtils.showLong(msg)
                                }
                            }
                        }
                    }
            }
        }
    }
}