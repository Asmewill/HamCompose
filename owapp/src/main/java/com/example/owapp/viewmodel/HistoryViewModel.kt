package com.example.owapp.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.owapp.base.BaseViewModel
import com.example.owapp.room.HistoryDatabase
import com.example.owapp.room.HistoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Owen on 2023/6/16
 */
class HistoryViewModel:BaseViewModel() {

    val historyListState = mutableStateOf<MutableList<HistoryItem>?>(null)

    fun getHistoryList(mContext:Context){
        viewModelScope.launch {
            val historyList=   withContext(Dispatchers.IO){
                HistoryDatabase.getInstance(mContext).historyDao().queryAll()
            }
            withContext(Dispatchers.Main){
                historyListState.value=historyList
            }
        }
    }
    fun clearAllHistory(mContext:Context){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                HistoryDatabase.getInstance(mContext =mContext ).historyDao().deleteAll()
            }
            withContext(Dispatchers.Main){
                getHistoryList(mContext = mContext)
            }
        }
    }



}