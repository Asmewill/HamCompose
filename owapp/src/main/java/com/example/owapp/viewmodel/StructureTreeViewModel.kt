package com.example.owapp.viewmodel

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
 * Created by Owen on 2023/5/23
 */
class StructureTreeViewModel :BaseViewModel(){
    val structLiveData = MutableLiveData<MutableList<ParentBean>>()

    val tabList = mutableStateOf(
        value= mutableListOf(
            "Android Studio相关",
            "Gradle","官方发布",
            "90-120hz","Git","Flutter"
        )
    )

    fun getStructureList(){
        async{
            HttpRepository.getStructureList().collectLatest {
                when(it){
                    is HttpResult.Success ->{
                        structLiveData.value=it.result
                    }
                    is HttpResult.Error ->{
                        viewModelScope.launch(Dispatchers.Main) {
                            ToastUtils.showShort("加载出错,请重试!!!")
                        }
                    }
                }
            }
        }
    }

}