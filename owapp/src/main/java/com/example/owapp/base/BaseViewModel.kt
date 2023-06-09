package com.example.owapp.base

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Owen on 2023/6/1
 */
open class BaseViewModel:ViewModel() {

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



}