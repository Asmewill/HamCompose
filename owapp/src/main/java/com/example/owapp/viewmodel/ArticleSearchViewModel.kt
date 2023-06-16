package com.example.owapp.viewmodel

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.base.BaseViewModel
import com.example.owapp.http.HttpRepository
import com.example.owapp.http.HttpResult
import com.example.owapp.room.HotKey
import com.example.owapp.room.HotkeyDatabase
import com.mm.hamcompose.data.bean.Article
import com.mm.hamcompose.data.bean.Hotkey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Owen on 2023/6/13
 */
class ArticleSearchViewModel:BaseViewModel() {
    val hotkeyLiveData=MutableLiveData<MutableList<Hotkey>>()
    val historyLiveData= MutableLiveData<List<HotKey>>()
    val searchLiveData=MutableLiveData<Flow<PagingData<Article>>>()

    fun getHotkey(){
        async {
            HttpRepository.getHotkeys().collectLatest {
                when(it){
                    is HttpResult.Success->{
                        hotkeyLiveData.value=it.result
                    }
                    is HttpResult.Error->{
                        it.exception.message?.let { msg->
                            ToastUtils.showLong(msg)
                        }
                    }
                }
            }
        }
    }

    fun getSearchHistoryList(mContext:Context){
        viewModelScope.launch(Dispatchers.IO) {//必须在IO线程
            val list=HotkeyDatabase.getInstance(mContext).hotKeyDao().queryAll()
            withContext(Dispatchers.Main){
                historyLiveData.value=list
            }
        }
    }

    fun addRecord(mContext:Context,item:HotKey){
        viewModelScope.launch(Dispatchers.IO) {//必须在IO线程
            HotkeyDatabase.getInstance(mContext).hotKeyDao().insertHistory(item)
            getSearchHistoryList(mContext)
        }
    }

    fun removeRecord(mContext: Context,item:HotKey){
        viewModelScope.launch(Dispatchers.IO) { //必须在IO线程
            HotkeyDatabase.getInstance(mContext = mContext).hotKeyDao().deleteHistory(item)
            getSearchHistoryList(mContext)
        }
    }
    fun removeAllRecord(mContext: Context){
        viewModelScope.launch(Dispatchers.IO) { //必须在IO线程
            HotkeyDatabase.getInstance(mContext = mContext).hotKeyDao().deleteAll()
            getSearchHistoryList(mContext)
        }
    }

    fun getSearchList(key:String){
        searchLiveData.value=HttpRepository.queryArticle(key).cachedIn(viewModelScope)
    }

}