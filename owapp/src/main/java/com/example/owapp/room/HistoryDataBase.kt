package com.example.owapp.room

import android.content.Context
import androidx.room.*
import com.mm.hamcompose.data.bean.HistoryRecord

/**
 * Created by Owen on 2023/6/16
 */

//1.定义entity 确定表结构
@Entity (tableName = "history")
data class HistoryItem(
    @PrimaryKey var id: Int,
    var title: String,
    var link: String,
    var niceDate: String,
    var shareUser: String,
    var userId: Int,
    var author: String,
    var superChapterId: Int,
    var superChapterName: String,
    var chapterId: Int,
    var chapterName: String,
    var desc: String,
)
//2.定义Dao层
@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(vararg history: HistoryItem)

    @Query("SELECT * FROM history")
    suspend fun queryAll(): MutableList<HistoryItem>

    @Query("DELETE FROM history")
    suspend fun deleteAll()
}
//3.定义database 数据库
@Database(entities = [HistoryItem::class], version = 1, exportSchema = false)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object{
        private const val DB_NAME = "history_db"
        //保证不同线程对这个共享变量进行操作的可见性，并且禁止进行指令重排序.
        @Volatile
        private var instance:HistoryDatabase?=null

        @Synchronized
        fun getInstance(mContext: Context):HistoryDatabase{
            if(instance==null){
                instance= Room.databaseBuilder(mContext,HistoryDatabase::class.java, DB_NAME).build()
            }
            return instance as HistoryDatabase
        }

    }
}