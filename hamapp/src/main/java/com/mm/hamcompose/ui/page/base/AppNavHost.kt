package com.mm.hamcompose.ui.page.base

import androidx.compose.foundation.background
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.mm.hamcompose.data.bean.ParentBean
import com.mm.hamcompose.data.bean.WebData
import com.mm.hamcompose.data.bean.WelfareData
import com.mm.hamcompose.theme.HamTheme
import com.mm.hamcompose.ui.page.girls.info.GirlInfoPage
import com.mm.hamcompose.ui.page.girls.list.GirlPhotoPage
import com.mm.hamcompose.ui.page.main.MainPage
import com.mm.hamcompose.ui.page.main.category.pubaccount.author.PublicAccountAuthorPage
import com.mm.hamcompose.ui.page.main.category.pubaccount.search.PublicAccountSearch
import com.mm.hamcompose.ui.page.main.category.share.ShareArticlePage
import com.mm.hamcompose.ui.page.main.category.structure.list.StructureListPage
import com.mm.hamcompose.ui.page.main.collection.edit.WebSiteEditPage
import com.mm.hamcompose.ui.page.main.home.search.SearchPage
import com.mm.hamcompose.ui.page.main.profile.history.HistoryPage
import com.mm.hamcompose.ui.page.main.profile.message.MessagePage
import com.mm.hamcompose.ui.page.main.profile.points.PointsRankingPage
import com.mm.hamcompose.ui.page.main.profile.settings.SettingsPage
import com.mm.hamcompose.ui.page.main.profile.sharer.SharerPage
import com.mm.hamcompose.ui.page.main.profile.user.LoginPage
import com.mm.hamcompose.ui.page.main.profile.user.RegisterPage
import com.mm.hamcompose.ui.page.webview.WebViewPage
import com.mm.hamcompose.ui.route.RouteName
import com.mm.hamcompose.ui.route.RouteUtils


@Composable
fun AppNavHost() {

    val navCtrl = rememberNavController()  //1.导航控制器
    val scaffoldState = rememberScaffoldState()

    NavHost(
        modifier = Modifier.background(HamTheme.colors.background),
        navController = navCtrl,
        startDestination = RouteName.MAIN
    ) {
        composable(RouteName.MAIN) {
            MainPage(navCtrl = navCtrl, scaffoldState = scaffoldState)
        }
        //Jetpack-Compose之四 页面跳转
        // https://blog.csdn.net/unreliable_narrator/article/details/122544446
        //文章搜索页
        composable(
            //传递一个Int类型的参数
            route = RouteName.ARTICLE_SEARCH + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            SearchPage(navCtrl, scaffoldState)
        }
        //看妹子
        composable(route = RouteName.GIRL_PHOTO) {
            GirlPhotoPage(navCtrl)
        }
        //看妹子(大图)
        composable(route = RouteName.GIRL_INFO) {
            val args = RouteUtils.getArguments<Any>(navCtrl)
            if (args != null && args is WelfareData) {
                GirlInfoPage(
                    welfare = args,
                    navCtrl = navCtrl,
                    scaffoldState = scaffoldState
                )
            }
        }
        //公众号详情
        composable(route = RouteName.PUB_ACCOUNT_DETAIL) {
            val args = RouteUtils.getArguments<Any>(navCtrl)
            if (args != null && args is ParentBean) {
                PublicAccountAuthorPage(
                    parent = args,
                    navCtrl = navCtrl,
                    scaffoldState = scaffoldState
                )
            }
        }
        //体系
        composable(route = RouteName.STRUCTURE_LIST) {
            val args = RouteUtils.getArguments<Any>(navCtrl) //获取参数
            if (args != null && args is ParentBean) {
                StructureListPage(  //通过构造方法传递参数
                    parent = args,
                    navCtrl = navCtrl,
                    scaffoldState = scaffoldState
                )
            }
        }

        //公众号搜索
        composable(route = RouteName.PUB_ACCOUNT_SEARCH) {
            val args = RouteUtils.getArguments<Any>(navCtrl)
            if (args != null && args is ParentBean) {
                PublicAccountSearch(
                    parent = args,
                    navCtrl = navCtrl,
                    scaffoldState = scaffoldState
                )
            }
        }

        //WebView
        composable(route = RouteName.WEB_VIEW) {
            //从导航中获取argument的值
            val args = RouteUtils.getArguments<Any>(navCtrl)
            if (args != null && args is WebData) {
                WebViewPage(webData = args, navCtrl = navCtrl)
            }
        }

        //登录
        composable(route = RouteName.LOGIN) {
            LoginPage(navCtrl, scaffoldState)
        }

        //注册
        composable(route = RouteName.REGISTER) {
            RegisterPage(navCtrl, scaffoldState)
        }
        //积分排行榜
        composable(route = RouteName.RANKING) {
            PointsRankingPage(navCtrl)
        }
        //消息
        composable(route = RouteName.MESSAGE) {
            MessagePage(navCtrl)
        }
        //设置
        composable(route = RouteName.SETTINGS) {
            SettingsPage(
                navCtrl = navCtrl,
                scaffoldState = scaffoldState
            )
        }
        //添加网址
        composable(route = RouteName.EDIT_WEBSITE) {
            val args = RouteUtils.getArguments<Any>(navCtrl)
            WebSiteEditPage(
                website = if (args != null && args is ParentBean) args else null,
                navCtrl = navCtrl,
                scaffoldState = scaffoldState
            )
        }

        // 作者/我的分享的文章列表
        composable(
            route = RouteName.SHARER + "/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) {
            SharerPage(
                userId = it.arguments?.getInt("userId"),//从NavBackStackEntry中提取NavArguments
                navCtrl = navCtrl,
                scaffoldState = scaffoldState
            )
        }
        //分享文章
        composable(route = RouteName.SHARE_ARTICLE) {
            ShareArticlePage(
                navCtrl = navCtrl,
                scaffoldState = scaffoldState
            )
        }
        composable(route = RouteName.HISTORY) {
            HistoryPage(navCtrl = navCtrl, scaffoldState = scaffoldState)
        }
    }
}

