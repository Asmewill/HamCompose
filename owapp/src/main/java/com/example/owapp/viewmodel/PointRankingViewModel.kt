package com.example.owapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.example.owapp.base.BaseViewModel
import com.mm.hamcompose.data.bean.TabTitle

/**
 * Created by Owen on 2023/5/26
 */
class PointRankingViewModel:BaseViewModel() {
    val titleList= mutableStateOf(
        value= mutableListOf(
           TabTitle(501,"排行榜"),
           TabTitle(502,"我的积分")
    ))



}