package com.example.owapp.http

/**
 * Created by Owen on 2023/5/18
 */
//extends out   必须是T或者T的子类 ,super in 必须是T或者T的父类
sealed class HttpResult<out T> {
    data class Success<T>(val result:T):HttpResult<T>()
    data class Error(val exception:Exception):HttpResult<Nothing>()

}