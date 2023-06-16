package com.example.owapp.util

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination

import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.navigation
import com.example.owapp.page.*
import com.example.owapp.route.BottomNavRoute
import com.example.owapp.route.RouteName
import com.mm.hamcompose.data.bean.ParentBean
import com.mm.hamcompose.data.bean.WebData

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
@Composable
fun MainPage(
    navCtrl: NavHostController = rememberNavController(),
    onFinish: () -> Unit
) {
   // var homeIndex by remember { mutableStateOf(0) }
    //返回back堆栈的顶部条目
    val navBackStackEntry by navCtrl.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: RouteName.HOME
    var index by remember {
        mutableStateOf(BottomNavRoute.Home.mipId)
    }
    if (isMainScreen(currentRoute)) {
        Scaffold(
            //这里可以使用公共的topBar防止出现页面切换闪烁问题
            content = {
                NavHostTab(navCtrl, onFinish, it)
            },
            bottomBar = {
                BottomNavBar(navCtrl,
                    index,
                    onItemSelect = {
                        index=it
                })
            }
        )
    } else {
        NavHostTab(navCtrl, onFinish)
    }

}

@Composable
fun NavHostTab(
    navCtrl: NavHostController,
    onFinish: () -> Unit,
    paddingValues: PaddingValues = PaddingValues(0.dp),
) {
    var homeIndex by remember {
        mutableStateOf(0)
    }
    var categoryIndex  by remember { mutableStateOf(0)}
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
                    HomePage(navCtrl, homeIndex = homeIndex) {
                        homeIndex = it
                    }
                    //点击两次返回才关闭app
                    BackHandler {
                        TwoBackFinish.execute(onFinish)
                    }
                }
                composable(RouteName.CATEGORY) {
                    CategoryPage(navCtrl = navCtrl, indexPage = categoryIndex, onPageSelected = {
                        categoryIndex=it
                    })
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
            //积分排行
            composable(RouteName.POINTRANKING) {
                PointRankingPage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
            //我的收藏
            composable(RouteName.MY_COLLECT) {
                MyCollectPage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
            //我的文章
            composable(RouteName.MY_ARTICLE) {
                MyArticlePage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
            //WebView
            composable(
                route=RouteName.WEBVIEW+"?url={url}&title={title}",
                arguments= listOf(
                     navArgument("url"){ defaultValue=""},
                     navArgument("title"){defaultValue=""}
                )
            ) {
                val title=it.arguments?.getString("title")?:""
                val url =it.arguments?.getString("url")?:""
                val parcelable= navCtrl.previousBackStackEntry?.arguments?.getParcelable<Parcelable>(Constant.ARGS)
                if(!TextUtils.isEmpty(title)&&!TextUtils.isEmpty(url)){
                    WebViewPage(navCtrl, title = title,url=url)
                }else{
                    if(parcelable!=null&&parcelable is WebData){
                        WebViewPage(navCtrl, webData = parcelable)
                    }
                }
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
            //系统设置
            composable(RouteName.SETTINGS) {
                SettingsPage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
            //搜索文章
            composable(RouteName.ARTICLE_SEARCH) {
                ArticleSearchPage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
            //添加收藏
            composable(RouteName.ADD_COLLECT) {
                AddCollectPage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
            //编辑收藏
            composable(RouteName.EDIT_COLLECT){
                val parcelable= navCtrl.previousBackStackEntry?.arguments?.getParcelable<Parcelable>(Constant.ARGS)
                if(parcelable!=null&&parcelable is ParentBean){
                    EditCollectPage(navCtrl = navCtrl, itemBean = parcelable)
                }
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
           // 添加分享文章
            composable(RouteName.SHARE_ARTICLE){
                ShareArticlePage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
           //学习 ---- 现在最多只能容纳11+1个主界面 12个页面，超出就报错了
            composable(RouteName.STUDY) {
                StudyPage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
            composable(RouteName.STRUCTURE_LIST) {
               val parcelable= navCtrl.previousBackStackEntry?.arguments?.getParcelable<Parcelable>(Constant.ARGS)
                if(parcelable!=null&&parcelable is ParentBean){
                    StructureListPage(navCtrl,parcelable)
                }
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
            //可以多加一个空的，防止:NavHost报错： java.lang.ArrayIndexOutOfBoundsException: length=13; index=13
            composable(RouteName.WECHAT_DETAIL) {
                val parcelable=navCtrl.previousBackStackEntry?.arguments?.getParcelable<Parcelable>(Constant.ARGS)
                if(parcelable!=null&&parcelable is ParentBean){
                    WechatDetailPage(navCtrl = navCtrl,parcelable)
                }
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
            composable(RouteName.WECHAT_SEARCH) {
                val parcelable=navCtrl.previousBackStackEntry?.arguments?.getParcelable<Parcelable>(Constant.ARGS)
                if(parcelable!=null&&parcelable is ParentBean){
                    WechatSearchPage(navCtrl =navCtrl,parcelable)
                }
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
            composable(RouteName.HISTORY_RECORD) {
                HistoryPage(navCtrl)
                BackHandler {
                    navCtrl.navigateUp()
                }
            }
            composable("17") {
            }
            composable("18") {
            }
            composable("19") {
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
    selectIndex:Int,
    onItemSelect:(Int)->Unit={}
) {

    BottomNavigation(backgroundColor = Color.White) {
        items.forEach { item ->
            val isSelected = selectIndex == item.mipId
            BottomNavigationItem(
                selected = isSelected, onClick = {
                    onItemSelect(item.mipId)
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

