package com.example.owapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.owapp.base.BaseViewModel
import com.example.owapp.http.HttpRepository
import com.mm.hamcompose.data.bean.PointsBean
import kotlinx.coroutines.flow.Flow

/**
 * Created by Owen on 2023/6/7
 */
class TabPointViewModel:BaseViewModel() {
    val recordLiveData = MutableLiveData<Flow<PagingData<PointsBean>>>()

    fun getPointRecordList(){
        recordLiveData.value = HttpRepository.getPointsRecords().cachedIn(viewModelScope)
    }

}