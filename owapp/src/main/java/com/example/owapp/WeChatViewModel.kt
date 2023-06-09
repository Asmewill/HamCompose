package com.example.owapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.base.BaseViewModel
import com.example.owapp.http.HttpRepository
import com.example.owapp.http.HttpResult
import com.mm.hamcompose.data.bean.ParentBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * 公众号
 * Created by Owen on 2023/5/23
 */
class WeChatViewModel:BaseViewModel() {
    val tabList = mutableStateOf(value= mutableListOf(
        "鸿洋","郭霖","玉刚说","承香墨影",
        "Android群英传","Code小生","Google开发者"
    ))
    val weChatLiveData= MutableLiveData<MutableList<ParentBean>>()
    fun getWeChatList(){
        async{
            HttpRepository.getPublicInformation().collectLatest {
                when(it){
                    is HttpResult.Success->{
                        weChatLiveData.value=it.result
                    }
                    is HttpResult.Error->{
                        viewModelScope.launch(Dispatchers.Main) {
                            ToastUtils.showLong("加载出错，请重试!!!")
                        }
                    }
                }
            }
        }
    }
}