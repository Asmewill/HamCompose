package com.example.owapp.room

import android.content.Context
import androidx.room.*
import com.mm.hamcompose.data.bean.Hotkey

/**
 * Created by Owen on 2023/6/12
 */
//1.定义entity 确定表结构
@Entity(tableName = "hot_key")
data class HotKey(
    @ColumnInfo
    val text:String
){
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
}
//2.定义Dao层
@Dao
interface HotKeyDao{
    //新增
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history:HotKey)

    //删除某一项
    @Delete
    fun deleteHistory(history:HotKey)
    //通过Id删除某一项
    @Query("delete from hot_key where id=:id")
    fun deleteById(id:Int)
    //删除全部
    @Query("delete from hot_key")
    fun  deleteAll()

    //查询全部
    @Query("select * from hot_key")
    fun queryAll():MutableList<HotKey>?
    //通过Id查询
    @Query("select * from hot_key where id=:id")
    fun queryById(id:Int):MutableList<HotKey>?
}
//3.定义database 数据库
@Database(entities=[HotKey::class], version = 1, exportSchema = false)
abstract class HotkeyDatabase:RoomDatabase(){
    abstract fun hotKeyDao():HotKeyDao

    companion object{
        private const val DB_NAME = "hotkey_db"
        //保证不同线程对这个共享变量进行操作的可见性，并且禁止进行指令重排序.
        @Volatile
        private var instance:HotkeyDatabase?=null

        @Synchronized
        fun getInstance(mContext:Context):HotkeyDatabase{
            if(instance==null){
                instance= Room.databaseBuilder(mContext,HotkeyDatabase::class.java, DB_NAME).build()
            }
            return instance as HotkeyDatabase
        }
    }
}

