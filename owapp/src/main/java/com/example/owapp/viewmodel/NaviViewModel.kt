package com.example.owapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.base.BaseViewModel
import com.example.owapp.http.HttpRepository
import com.example.owapp.http.HttpResult
import com.mm.hamcompose.data.bean.NaviWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Owen on 2023/5/23
 */
class NaviViewModel:BaseViewModel() {
    val tabList=mutableStateOf<MutableList<String>>(value= mutableListOf(
        "Google开发者","GitHub","掘金","CSDN","简书"
        ,"开发者头条","国内大牛","Android源码","maven仓库",
       "AndroidSDK查看"
    ))
    val navLiveData = MutableLiveData<MutableList<NaviWrapper>>()

    fun getNavList(){
        async{
            HttpRepository.getNavigationList().collectLatest {
                when(it){
                   is HttpResult.Success ->{
                       navLiveData.value=it.result
                    }
                   is HttpResult.Error ->{
                       viewModelScope.launch (Dispatchers.Main){
                           ToastUtils.showLong("数据请求失败，请重试")
                       }
                   }
                }
            }
        }
    }



}