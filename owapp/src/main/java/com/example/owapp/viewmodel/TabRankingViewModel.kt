package com.example.owapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.base.BaseViewModel
import com.example.owapp.http.HttpRepository
import com.example.owapp.http.HttpResult
import com.mm.hamcompose.data.bean.PointItem
import com.mm.hamcompose.data.bean.PointsBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Owen on 2023/6/7
 */
class TabRankingViewModel:BaseViewModel() {
    val itemList =mutableStateOf(value= mutableListOf(
        PointItem(userName = "g**eil", score = 606, rank = 12),
        PointItem(userName = "深**士", score = 607, rank = 13),
        PointItem(userName = "1**12235", score = 608, rank = 14),
        PointItem(userName = "dstest0004", score = 609, rank = 15),
        PointItem(userName = "dstest0005", score = 610, rank = 16),
        PointItem(userName = "dstest0006", score = 611, rank = 17),
    ))
    val myPointLiveData=MutableLiveData<PointsBean>()
    val rankListLiveData=MutableLiveData<Flow<PagingData<PointsBean>>>()

    fun getMyPoint(){
        async {
            HttpRepository.getMyPointsRanking().collectLatest {
                when(it){
                    is HttpResult.Success ->{
                        myPointLiveData.value=it.result
                    }
                    is HttpResult.Error ->{
                        ToastUtils.showLong("加载出错,请重试!!!")
                    }
                }
            }
        }
    }

    fun getPointRankingList(){
        rankListLiveData.value=  HttpRepository.getPointsRankings().cachedIn(viewModelScope)
    }
}