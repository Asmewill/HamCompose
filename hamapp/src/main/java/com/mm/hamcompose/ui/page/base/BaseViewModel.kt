package com.mm.hamcompose.ui.page.base

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel<T> : ViewModel() {

    //分类列表（装非分页加载的容器）
    var list = mutableStateOf(mutableListOf<T>())

    var currentListIndex = mutableStateOf(0)

    var loading = mutableStateOf(false)

    private var _isInited = mutableStateOf(false)

    var message = mutableStateOf("")

    private val isInited: Boolean
        get() = _isInited.value


    abstract fun start()


    //重置index
    fun resetListIndex() {
        currentListIndex.value = 0
    }
    //请求初始化
    private fun requestInitialized() {
        _isInited.value = true
    }
    //重置初始化的值
    fun resetInitState() {
        _isInited.value = false
    }
    //协程异步处理任务
    fun async(block: suspend ()-> Unit) {
        viewModelScope.launch { block() }
    }

    //初始化
    fun initThat(block: () -> Unit) {
        if (!isInited) {
            block.invoke()
            requestInitialized()
        }
    }

    fun savePosition(index: Int) {
        currentListIndex.value = index
        println("## save position = $index ##")
    }

    fun stopLoading() {
        loading.value = false
    }

    fun startLoading() {
        loading.value = true
    }

    open fun loadContent() {

    }

}