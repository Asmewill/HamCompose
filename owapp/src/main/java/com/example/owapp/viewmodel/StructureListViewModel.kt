package com.example.owapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.owapp.base.BaseViewModel
import com.example.owapp.http.HttpRepository
import com.mm.hamcompose.data.bean.Article
import kotlinx.coroutines.flow.Flow

/**
 * Created by Owen on 2023/6/13
 */
class StructureListViewModel:BaseViewModel() {

    val structureLiveData=MutableLiveData<Flow<PagingData<Article>>>()


    fun getStructureList(cid:Int){
        structureLiveData.value=HttpRepository.getStructureArticles(cid).cachedIn(viewModelScope)
    }

    fun searchByAuthor(author:String){
        structureLiveData.value=HttpRepository.getStructureArticles(author).cachedIn(viewModelScope)
    }

}