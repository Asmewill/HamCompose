package com.example.owapp.util

import android.content.Context
import androidx.compose.runtime.Composable
import com.blankj.utilcode.util.ToastUtils

/**
 * Created by Owen on 2023/5/19
 */
object TwoBackFinish {

    var mExitTime:Long=0
    fun execute(onFinish:()->Unit){
        when{
            (System.currentTimeMillis()- mExitTime)>1500->{
                ToastUtils.showShort("在按一次退出程序")
                mExitTime=System.currentTimeMillis()
            }else ->{
                onFinish()
               mExitTime=0
            }
        }
    }
}