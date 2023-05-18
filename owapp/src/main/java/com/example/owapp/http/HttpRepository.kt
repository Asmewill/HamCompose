package com.example.owapp.http

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mm.hamcompose.data.bean.BannerBean
import com.mm.hamcompose.data.bean.BasicUserInfo
import com.mm.hamcompose.data.bean.WelfareBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by Owen on 2023/5/18
 */
/***
 * 使用flow进行异步处理
 */
class HttpRepository:BaseRepository() {
//    //banner
//     suspend fun getBanners() = flowable { apiService.getBanners() }
//    //置顶文章
//     suspend fun getTopArticles() = flowable { apiService.getTopArticles() }
//    //热门标签
//     suspend fun getHotkeys()= flowable { apiService.getHotkeys() }
//    //体系分类列表
//     suspend fun getStructureList()= flowable { apiService.getStructureList() }
//    //导航分类列表
//     suspend fun getNavigationList() = flowable { apiService.getNavigationList() }
//    //公众号作者列表
//     suspend fun getPublicInformation() = flowable { apiService.getPublicInformation() }
//    //项目分类
//     suspend fun getProjectCategory() = flowable { apiService.getProjectCategory() }
//    //注册
//     suspend fun register(userName: String, password: String, repassword: String)=flowable { apiService.register(userName, password, repassword) }
//    //登录
//     suspend fun login(userName: String, password: String)=flowable { apiService.login(userName, password) }
//    //退出登录
//     suspend fun logout() = flowable { apiService.logout() }
//    //我的积分排行
//     suspend fun getMyPointsRanking() = flowable { apiService.getMyPointsRanking() }
//     suspend fun getMessageCount() = flowable { apiService.getMessageCount() }
//     suspend fun getCollectUrls() = flowable { apiService.getCollectUrls() }
//     suspend fun collectInnerArticle(id: Int)=flowable { apiService.collectInnerArticle(id)
//     suspend fun uncollectInnerArticle(id: Int)=flowable { apiService.uncollectInnerArticle(id) }
//     suspend fun uncollectArticleById(id: Int, originId: Int)=flowable { apiService.uncollectArticleById(id, originId) }
//     suspend fun addNewWebsiteCollect(title: String, linkUrl: String)= flowable { apiService.addNewWebsiteCollect(title, linkUrl) }
//
//     suspend fun addNewArticleCollect(title: String, linkUrl: String, author: String)=flowable { apiService.addNewArticleCollect(title, linkUrl, author) }
//
//     suspend fun deleteWebsite(id: Int) = flowable { apiService.deleteWebsite(id) }
//
//     suspend fun editCollectWebsite(id: Int, title: String, linkUrl: String)=flowable { apiService.editCollectWebsite(id, title, linkUrl) }
//
//     suspend fun getMyShareArticles(page: Int)= flowable { apiService.getMyShareArticles(page) }
//
//     suspend fun getAuthorShareArticles(userId: Int, page: Int)=flowable { apiService.getAuthorShareArticles(userId, page) }
//
//     suspend fun deleteMyShareArticle(articleId: Int)=flowable { apiService.deleteMyShareArticle(articleId) }
//
//     suspend fun addMyShareArticle(title: String, link: String, shareUser: String)=flowable { apiService.addMyShareArticle(title, link, shareUser) }
//
//     suspend fun getBasicUserInfo() =flowable { apiService.getBasicUserInfo() }

//    //福利
//     suspend fun getWelfareData(page: Int, pageSize: Int): Flow<HttpResult<WelfareBean>> {
//        return flow {
//            val result =  try {
//                val response = apiService.getWelfareList("Girl", "Girl", page, pageSize)
//                if (response.data != null) {
//                    HttpResult.Success(response)
//                } else {
//                    throw Exception("the result of remote's request is null")
//                }
//            } catch (ex: Exception) {
//                HttpResult.Error(ex)
//            }
//            emit(result)
//        }.flowOn(Dispatchers.IO)
//    }

//    /** 首页列表 */
//     fun getIndexData() = pager { page->  apiService.getIndexList(page) }
//
//    /** 广场列表 */
//     fun getSquareData() = pager { page->  apiService.getSquareData(page) }
//
//    /** 问答列表 */
//     fun getWendaData() = pager { page-> apiService.getWendaData(page) }
//
//    /** 分类项目 （根据cid区分项目）*/
//     fun getProjects(cId: Int) = pager { page ->
//        // -1=无分类，加载热门项目
//        if (cId == -1) {
//            apiService.getHotProjects(page)
//        } else {
//            apiService.getProjects(page, cId)
//        }
//    }

    /** 公众号文章 */
   // fun getPublicArticles(publicId: Int) = pager { apiService.getPublicArticles(publicId, it) }

//    /** 体系 */
//     fun getStructureArticles(param: Any): PagingArticle {
//        return pager { page ->
//            when (param) {
//                is String -> apiService.getArticlesByAuthor(page, param)
//                else -> apiService.getStructureArticles(page, param as Int)
//            }
//        }
//    }
//
//    /** 搜索公众号*/
//     fun searchArticleWithKey(publicId: Int, key: String): PagingArticle {
//        return pager{ page ->
//            apiService.getPublicArticlesWithKey(publicId, page, key)
//        }
//    }
//
//    /** 搜索文章*/
//     fun queryArticle(key: String) = pager { page -> apiService.queryArticle(page, key) }
//
//    /** 积分排行榜 */
//     fun getPointsRankings(): PagingPoints {
//        return pager(
//            initKey = 1,
//            baseConfig = PagingConfig(
//                pageSize = 30,
//                enablePlaceholders = true,
//                prefetchDistance = 10
//            )
//        ) { page -> apiService.getPointsRankings(page) }
//    }
//
//    /** 积分记录 */
//     fun getPointsRecords() = pager(initKey = 1) { page -> apiService.getPointsRecords(page) }
//    /** 收藏列表 */
//     fun getCollectionList() = pager { page -> apiService.getCollectionList(page) }
//    /** 未读消息 */
//     fun getUnreadMessages() = pager(initKey = 1) { page -> apiService.getUnreadMessage(page) }
//    /** 已读消息 */
//     fun getReadedMessages() = pager(initKey = 1) { page -> apiService.getReadedMessage(page) }
//
//    /** 看妹纸*/
//     fun getWelfareData(key: String): PagingWelfare {
//        return Pager(
//            config = PagingFactory().pagingConfig,
//            pagingSourceFactory = {
//                GirlPhotoPagingSource(apiService)
//            }
//        ).flow
//    }
    
}