package com.example.owapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.base.BaseViewModel
import com.example.owapp.http.HttpRepository
import com.example.owapp.http.HttpResult
import com.example.owapp.util.Constant
import com.mm.hamcompose.data.bean.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Owen on 2023/6/6
 */
class LoginViewModel:BaseViewModel() {
    val loginLiveData =MutableLiveData<UserInfo>()
    fun toLogin(userName:String ,pwd:String){
        async {
            HttpRepository.login(userName = userName, password = pwd).collectLatest {
                when(it){
                    is HttpResult.Success->{
                        loginLiveData.value=it.result
                    }
                    is HttpResult.Error->{
                        viewModelScope.launch(Dispatchers.Main) {
                            ToastUtils.showLong("加载出错,请重试!!!")
                        }
                    }
                }
            }
        }
    }
}