package com.example.owapp.base

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.http.HttpRepository
import com.example.owapp.http.HttpResult
import com.example.owapp.room.HistoryDatabase
import com.example.owapp.room.HistoryItem
import com.mm.hamcompose.data.bean.Article
import com.mm.hamcompose.data.bean.HistoryRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Owen on 2023/6/1
 */
open class BaseViewModel:ViewModel() {
      //从详情页返回时，保持列表所在的滑动位置
      var currentListIndex = mutableStateOf(0)
      //收藏
      var collectType = mutableStateOf(0)

      var isInit= false
      var isLoading=  mutableStateOf(true)
      /***
       *消息异步处理
       */
      fun async(block:suspend ()->Unit){
            viewModelScope.launch {
                  block()
            }
      }

      fun delayTime(mills:Long){
            viewModelScope.launch {
                delay(mills)
            }
      }

      fun startLoading(){
          isLoading.value=true
      }
      fun stopLoading(){
            isLoading.value=false
      }


      fun collectArticleById(id:Int){
            async {
                  HttpRepository.collectInnerArticle(id).collectLatest {
                        when(it){
                              is HttpResult.Success->{

                              }
                              is HttpResult.Error->{
                                    it.exception.message?.let {
                                          if(it.equals("504")){
                                                collectType.value=1
                                                ToastUtils.showLong("收藏成功")
                                          }
                                    }
                              }
                        }
                  }
            }
      }

      fun uncollectArticleById(id:Int){
            async {
                  HttpRepository.uncollectInnerArticle(id).collectLatest {
                        when(it){
                              is HttpResult.Success->{

                              }
                              is HttpResult.Error->{
                                    it.exception.message?.let {
                                          if(it.equals("504")){
                                                collectType.value=2
                                                ToastUtils.showLong("取消收藏")
                                          }
                                    }
                              }
                        }
                  }
            }
      }

      fun cacheHistory(article: Article,mContext:Context) {
            async {
                  val history = toMapData(article)
                  withContext(Dispatchers.IO) {
                        HistoryDatabase.getInstance(mContext).historyDao().insertHistory(history)
                        println("成功储存到历史记录")
                  }
            }
      }
      private fun toMapData(article: Article): HistoryItem {
            return with(article) {
                  HistoryItem(
                        id = id,
                        title = title ?: "",
                        link = link ?: "",
                        niceDate = niceDate ?: "",
                        shareUser = shareUser ?: "",
                        userId = userId,
                        author = author ?: "",
                        superChapterId = superChapterId,
                        superChapterName = superChapterName ?: "",
                        chapterId = chapterId,
                        chapterName = chapterName ?: "",
                        desc = desc ?: ""
                  )
            }
      }

}