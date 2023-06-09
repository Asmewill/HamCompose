package com.example.owapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.base.BaseViewModel
import com.example.owapp.http.HttpRepository
import com.example.owapp.http.HttpResult
import com.mm.hamcompose.data.bean.Article
import com.mm.hamcompose.data.bean.ParentBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Owen on 2023/5/23
 */
class ProjectViewModel:BaseViewModel() {
//    val tabList= mutableStateOf(value= mutableListOf(
//        "最新","完整项目","跨平台应用","资源聚合类","动画","RV列表动画","项目基础功能"
//    ))
    val projectLiveData = MutableLiveData<MutableList<ParentBean>>()
    val projectItemListLiveData = MutableLiveData<Flow<PagingData<Article>>>()

    fun getProjectTabList(){
        async {
            HttpRepository.getProjectCategory().collectLatest {
                when(it){
                    is HttpResult.Success ->{
                       it.result?.let { list->
                           projectLiveData.value=list
                           if(list.size>0){
                               getProjectItemList(list[0].id)
                           }
                       }
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

    fun getProjectItemList(projectId:Int){
        projectItemListLiveData.value=   HttpRepository.getProjects(projectId).cachedIn(viewModelScope)
    }
}