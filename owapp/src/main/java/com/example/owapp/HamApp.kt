package com.example.owapp

import android.app.Application
import android.content.Context
import com.example.owapp.util.DataStoreUtils

/**
 * Created by Owen on 2023/5/30
 */
class HamApp:Application() {
    companion object {
        lateinit var mContext:Context
    }

    override fun onCreate() {
        super.onCreate()
        mContext=this
        DataStoreUtils.init(this)
    }
}