package com.example.owapp.http

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.owapp.http.paging.BasePagingSource
import com.example.owapp.http.paging.PagingFactory
import com.mm.hamcompose.data.bean.BasicBean
import com.mm.hamcompose.data.bean.ListWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

/**
 * Created by Owen on 2023/5/18
 */
open class BaseRepository {
    fun <T> flowable(call:suspend ()-> BasicBean<T>): Flow<HttpResult<T>> {
             return flow {
                 val result=try {
                     val response=call()
                     if(response.errorCode==0){
                         if(response.data!=null){
                             HttpResult.Success(response.data!!)
                         }else{
                             HttpResult.Error(Exception("504"))
                         }
                     }else{
                         HttpResult.Error(Exception(response.errorMsg))
                     }
                 }catch (e:Exception){
                     HttpResult.Error(Exception(e.message))
                 }
                emit(result)
             }.flowOn(Dispatchers.IO)
    }

    fun <T:Any> pager(
        initKey:Int=0,
        baseConfig:PagingConfig=PagingFactory().pagingConfig,
        callAction:suspend (Int)-> BasicBean<ListWrapper<T>>
    ):Flow<PagingData<T>>{
        return Pager(
            config=baseConfig,
            initialKey = initKey,
            pagingSourceFactory={
                     BasePagingSource(callAction ={ nextPage->
                         try {
                          return@BasePagingSource   HttpResult.Success(callAction(nextPage))
                         }catch (e:Exception){
                          return@BasePagingSource  HttpResult.Error(e)
                         }
                     })
            }
        ).flow
    }

}