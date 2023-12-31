package com.mm.hamcompose.data.bean

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


const val MY_USER_ID = -999

data class MenuTitle(
    val title: String,
    val iconRes: Int?
)

data class TabTitle(
    val id: Int,
    val text: String,
    var cachePosition: Int = 0,
    var selected: Boolean = false
)


data class HistoryRecord(
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

data class PointItem(
    val userName:String,
    val score:Int,
    val rank:Int
)
data class SignBean(
    val pointStr:String,
    val pointType:String
)

@Parcelize
data class WebData(
    var title: String,
    var url: String
): Parcelable
