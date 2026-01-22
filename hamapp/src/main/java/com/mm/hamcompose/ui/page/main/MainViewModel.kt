package com.mm.hamcompose.ui.page.main

import androidx.compose.runtime.mutableStateOf
import com.mm.hamcompose.data.bean.Article
import com.mm.hamcompose.data.bean.PointsBean
import com.mm.hamcompose.data.bean.UserInfo
import com.mm.hamcompose.data.db.user.UserInfoDatabase
import com.mm.hamcompose.data.http.HttpResult
import com.mm.hamcompose.data.http.repository.HttpRepository
import com.mm.hamcompose.ui.page.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: HttpRepository,
    private val db: UserInfoDatabase
) : BaseViewModel<Article>() {


    override fun start() {

    }

    // 当前页面索引
    private val _currentPageIndex = MutableStateFlow(0)
    val currentPageIndex: StateFlow<Int> = _currentPageIndex.asStateFlow()

    fun updatePageIndex(index: Int) {
        // 用户滑动导致的页面变化时，立即更新状态
        _currentPageIndex.value = index

    }

    private val _homeIndex = MutableStateFlow(0)
    val homeIndex: StateFlow<Int> = _homeIndex.asStateFlow()

    fun updateHomeIndex(index: Int) {
        // 用户滑动导致的页面变化时，立即更新状态
        _homeIndex.value = index

    }

    private val _categoryIndex = MutableStateFlow(0)
    val categoryIndex: StateFlow<Int> = _categoryIndex.asStateFlow()
    fun updateCategoryIndex(index: Int) {
        // 用户滑动导致的页面变化时，立即更新状态
        _categoryIndex.value = index

    }
}