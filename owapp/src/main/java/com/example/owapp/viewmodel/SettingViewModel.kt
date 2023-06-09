package com.example.owapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.base.BaseViewModel
import com.example.owapp.http.HttpRepository
import com.example.owapp.http.HttpResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Owen on 2023/6/6
 */
class SettingViewModel:BaseViewModel() {
    val logoutLiveData = MutableLiveData<Boolean>(false)
    fun logout(){
        async {
            HttpRepository.logout().collectLatest {
                when(it){
                    is HttpResult.Success->{
                        viewModelScope.launch(Dispatchers.Main) {
                            ToastUtils.showLong("Success")
                        }
                    }
                    is HttpResult.Error ->{
                        //退出登录，走Error
                        logoutLiveData.value=true
                    }
                }
            }
        }
    }

}