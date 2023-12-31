package com.mm.hamcompose.ui.page.main.home.project

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mm.hamcompose.data.bean.ParentBean
import com.mm.hamcompose.data.http.HttpResult
import com.mm.hamcompose.data.http.repository.HttpRepository
import com.mm.hamcompose.data.http.repository.PagingArticle
import com.mm.hamcompose.ui.page.base.BaseCollectViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private var httpRepo: HttpRepository
): BaseCollectViewModel<ParentBean>(httpRepo) {

    var tabIndex = mutableStateOf(-1)
    var isRefreshing = mutableStateOf(true)
    var pagingData = MutableLiveData<PagingArticle?>(null)
    var projectId = mutableStateOf(-1)//默认是最新
    var currentRowIndex = mutableStateOf(0)

    override fun start() {
        initThat {
            loadCategory()
            loadContent()
        }

    }

    fun setupProjectId(id: Int) {
        this.projectId.value = id
    }

    /**
     * 触发刷新机制
     */
    fun triggerRefresh() {
        isRefreshing.value = true
    }

    fun setTabIndex(index: Int) {
        tabIndex.value = index
    }

    private fun loadCategory() {
        async {
            httpRepo.getProjectCategory()
                .collectLatest { response ->
                    when (response) {
                        is HttpResult.Success -> {
                            list.value = response.result
                            tabIndex.value = 0
                        }
                        is HttpResult.Error -> {
                            println(response.exception.message)
                        }
                    }
                }
        }
    }

    private fun getProjects() = httpRepo.getProjects(projectId.value).cachedIn(viewModelScope)

    fun refresh() {
        pagingData.value = null
        loadContent()
    }

    override fun loadContent() {
        pagingData.value = getProjects()
        isRefreshing.value = pagingData.value==null
    }

    fun saveRowPosition(position: Int) {
        currentRowIndex.value = position
    }

}
