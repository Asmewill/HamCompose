package com.example.owapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.base.BaseViewModel
import com.example.owapp.http.HttpRepository
import com.example.owapp.http.HttpResult
import com.mm.hamcompose.data.bean.BasicUserInfo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Owen on 2023/6/6
 */
class MineViewModel :BaseViewModel() {
    val mineLiveData= MutableLiveData<BasicUserInfo>()

    fun getUserInfo(){
        async {
            HttpRepository.getBasicUserInfo().collectLatest {
                when(it){
                    is HttpResult.Success->{
                        mineLiveData.value= it.result
                    }
                    is HttpResult.Error ->{
                        viewModelScope.launch {
                            ToastUtils.showLong("加载出错，请重试!!!")
                        }
                    }
                }
            }
        }
    }
}