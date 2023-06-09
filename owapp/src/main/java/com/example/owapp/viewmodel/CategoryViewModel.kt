package com.example.owapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.mm.hamcompose.data.bean.TabTitle

/**
 * Created by Owen on 2023/5/19
 */
class CategoryViewModel {
    val titles= mutableStateOf<MutableList<TabTitle>>(
        mutableListOf<TabTitle>(
            TabTitle(201,"体系"),
            TabTitle(202,"导航"),
            TabTitle(203,"公众号")
        )
    )
}