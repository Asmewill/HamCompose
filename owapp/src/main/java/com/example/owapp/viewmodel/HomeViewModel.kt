package com.example.owapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.mm.hamcompose.data.bean.TabTitle

/**
 * Created by Owen on 2023/5/19
 */
class HomeViewModel {

    val titles= mutableStateOf<MutableList<TabTitle>>(
      value=  mutableListOf<TabTitle>(
            TabTitle(101,"推荐"),
            TabTitle(102,"广场"),
            TabTitle(103,"问答")
        )
    )

}