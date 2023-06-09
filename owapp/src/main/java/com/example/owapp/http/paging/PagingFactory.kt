package com.example.owapp.http.paging

import androidx.paging.PagingConfig

/**
 * Created by Owen on 2023/5/18
 */
class PagingFactory {
    val pagingConfig=PagingConfig(
        pageSize=20,   // 每页显示的数据的大小
        enablePlaceholders = true, //开启占位符
        prefetchDistance=4,         //预刷新的距离，距离最后一个 item 多远时加载数据
        //初始化加载数量，默认为 pageSize * 3
        initialLoadSize = 1
    )
}