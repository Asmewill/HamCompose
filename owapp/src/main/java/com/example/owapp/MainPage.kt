package com.example.owapp.util

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination

import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.navigation
import com.example.owapp.page.*
import com.example.owapp.route.BottomNavRoute
import com.example.owapp.route.RouteName
import com.mm.hamcompose.data.bean.ParentBean

/**
 *
 *
文本 -> stringResource(R.string.app_name)
颜色 -> colorResource(R.color.black)
尺寸 -> dimensionResource(R.dimen.padding_small)
图片 -> painterResource(R.drawable.ic_logo)
 *
 *
 * Created by Owen on 2023/5/18
 */
//val bottomNavRoute = mutableStateOf<BottomNavRoute>(BottomNavRoute.Home)
@Composable
fun MainPage(
    navCtrl: NavHostController = rememberNavController(),
    onFinish: () -> Unit
) {
    var homeIndex by remember { mutableStateOf(0) }
    //返回back堆栈的顶部条目
    val navBackStackEntry by navCtrl.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: RouteName.HOME
    if (isMainScreen(currentRoute)) {
        Scaffold(
            //这里可以使用公共的topBar防止出现页面切换闪烁问题
            content = {
                NavHostTab(navCtrl, homeIndex, onFinish, it)
            },
            bottomBar = {
                BottomNavBar(navCtrl, BottomNavRoute.Home)
            }
        )
    } else {
        NavHostTab(navCtrl, homeIndex, onFinish)
    }

}

@Composable
fun NavHostTab(
    navCtrl: NavHostController,
    homeIndex: Int,
    onFinish: () -> Unit,
    paddingValues: PaddingValues = PaddingValues(0.dp),
) {
    var homeIndex1 = homeIndex
    NavHost(
        navController = navCtrl,
        startDestination = RouteName.MAIN,
        modifier = Modifier.padding(paddingValues),
        builder = {
            navigation(
                route = RouteName.MAIN,
                startDestination = RouteName.HOME
            ) {
                composable(RouteName.HOME) {
                    HomePage(navCtrl, homeIndex = homeIndex1) {
                        homeIndex1 = it
                    }
                    //点击两次返回才关闭app
                    BackHandler {
                        TwoBackFinish.execute(onFinish)
                    }
                }
                composable(RouteName.CATEGORY) {
                    CategoryPage(navCtrl = navCtrl)
                    //点击两次返回才关闭app
                    BackHandler {
                        TwoBackFinish.execute(onFinish)
                    }
                }
                composable(RouteName.PROJECT) {
                    ProjectPage(navCtrl = navCtrl)
                    //点击两次返回才关闭app
                    BackHandler {
                        TwoBackFinish.execute(onFinish)
                    }
                }
                composable(RouteName.MINE) {
                    MinePage(navCtrl)
                    //点击两次返回才关闭app
                    BackHandler {
                        TwoBackFinish.execute(onFinish)
                    }
                }
            }
//            //登录
//            composable(RouteName.LOGIN) {
//                LoginPage(navCtrl)
//                BackHandler {
//                    navCtrl.navigateUp()
//                }
//            }
//            //注册
//            composable(RouteName.REGISTER) {
//                RegisterPage(navCtrl)
//                BackHandler {
//                    navCtrl.navigateUp()
//                }
//            }
//            //积分排行
//            composable(RouteName.POINTRANKING) {
//                PointRankingPage(navCtrl)
//                BackHandler {
//                    navCtrl.navigateUp()
//                }
//            }
//            //我的收藏
//            composable(RouteName.MY_COLLECT) {
//                MyCollectPage(navCtrl)
//                BackHandler {
//                    navCtrl.navigateUp()
//                }
//            }
//            //我的文章
//            composable(RouteName.MY_ARTICLE) {
//                MyArticlePage(navCtrl)
//                BackHandler {
//                    navCtrl.navigateUp()
//                }
//            }
//            //WebView
//            composable(
//                route=RouteName.WEBVIEW+"?url={url}&title={title}",
//                arguments= listOf(
//                     navArgument("url"){ defaultValue=""},
//                     navArgument("title"){defaultValue=""}
//                )
//            ) {
//                WebViewPage(navCtrl,it.arguments?.getString("title")?:"",it.arguments?.getString("url")?:"")
//                BackHandler {
//                    navCtrl.navigateUp()
//                }
//            }
//            //系统设置
//            composable(RouteName.SETTINGS) {
//                SettingsPage(navCtrl)
//                BackHandler {
//                    navCtrl.navigateUp()
//                }
//            }
//            //搜索文章
//            composable(RouteName.ARTICLE_SEARCH) {
//                ArticleSearchPage(navCtrl)
//                BackHandler {
//                    navCtrl.navigateUp()
//                }
//            }
//            //添加收藏
//            composable(RouteName.ADD_COLLECT) {
//                AddCollectPage(navCtrl)
//                BackHandler {
//                    navCtrl.navigateUp()
//                }
//            }
//            //编辑收藏
//            composable(RouteName.EDIT_COLLECT){
//                val args= navCtrl.previousBackStackEntry?.arguments?.getParcelable(Constant.ARGS) as ParentBean?
//                EditCollectPage(navCtrl = navCtrl, itemBean = args!!)
//                BackHandler {
//                    navCtrl.navigateUp()
//                }
//           }
//           // 添加分享文章
//            composable(RouteName.SHARE_ARTICLE){
//                ShareArticlePage(navCtrl)
//                BackHandler {
//                    navCtrl.navigateUp()
//                }
//            }
//          //学习 ---- 现在最多只能容纳11+1个主界面 12个页面，超出就报错了
//            composable(RouteName.STUDY) {
//                StudyPage(navCtrl)
//                BackHandler {
//                    navCtrl.navigateUp()
//                }
//            }
            //登录
            composable(RouteName.LOGIN) {
                LoginPage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }

            }
            //注册
            composable(RouteName.REGISTER) {
                RegisterPage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }

            }
            composable(RouteName.POINTRANKING) {
                PointRankingPage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
            composable(RouteName.MY_COLLECT) {
                MyCollectPage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
            composable(RouteName.MY_ARTICLE) {
                MyArticlePage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
            composable(
                route = RouteName.WEBVIEW + "?url={url}&title={title}",
                arguments = listOf(
                    navArgument("url") { defaultValue = "" },
                    navArgument("title") { defaultValue = "" })
            ) {
                WebViewPage(
                    navCtrl,
                    it.arguments?.getString("title") ?: "",
                    it.arguments?.getString("url") ?: ""
                )
                BackHandler {
                    navCtrl.navigateUp()
                }

            }
            composable(RouteName.SETTINGS) {
                SettingsPage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
            composable(RouteName.ARTICLE_SEARCH) {
                ArticleSearchPage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }

            }
            composable(RouteName.ADD_COLLECT) {
                AddCollectPage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }

            }
            composable(RouteName.EDIT_COLLECT) {
                val args =
                    navCtrl.previousBackStackEntry?.arguments?.getParcelable(Constant.ARGS) as ParentBean?
                EditCollectPage(navCtrl = navCtrl, itemBean = args!!)
                BackHandler {
                    navCtrl.navigateUp()
                }

            }
            composable(RouteName.SHARE_ARTICLE) {
                ShareArticlePage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }

            }
            composable(RouteName.STUDY) {
                StudyPage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }

            }
            composable("13") {

            }
            composable("14") {

            }
            composable("15") {

            }
            composable("16") {

            }
            composable("17") {

            }
        })
}

fun isMainScreen(route: String): Boolean = when (route) {
    RouteName.HOME, RouteName.CATEGORY, RouteName.PROJECT, RouteName.MINE
    -> true
    else
    -> false
}

val items = listOf(
    BottomNavRoute.Home,
    BottomNavRoute.Category,
    BottomNavRoute.Project,
    BottomNavRoute.Mine
)

@Composable
fun BottomNavBar(
    navCtrl: NavHostController,
    itemSelect: BottomNavRoute
) {
    var index by remember {
        mutableStateOf(itemSelect.mipId)
    }
    BottomNavigation(backgroundColor = Color.White) {
        items.forEach { item ->
            val isSelect = index == item.mipId
            BottomNavigationItem(
                selected = isSelect, onClick = {
                    index = item.mipId
                    navCtrl.navigate(item.routeName) {
                        popUpTo(navCtrl.graph.findStartDestination().id) {
                            saveState = true
                        }
                        //避免重建
                        launchSingleTop = true
                        //重新选择以前选择的项目时，恢复状态
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.mipId), contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(text = stringResource(id = item.stringId))
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.Black
            )
        }
    }
}

