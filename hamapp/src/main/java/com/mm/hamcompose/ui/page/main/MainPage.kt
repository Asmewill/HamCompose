package com.mm.hamcompose.ui.page.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.blankj.utilcode.util.ToastUtils
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mm.hamcompose.R
import com.mm.hamcompose.ui.page.main.category.CategoryPage
import com.mm.hamcompose.ui.page.main.category.share.ShareArticleViewModel
import com.mm.hamcompose.ui.page.main.collection.CollectionPage
import com.mm.hamcompose.ui.page.main.home.HomePage
import com.mm.hamcompose.ui.page.main.profile.ProfilePage
import com.mm.hamcompose.ui.route.BottomNavRoute
import com.mm.hamcompose.ui.route.BottomNavRouteNew
import com.mm.hamcompose.ui.route.RouteName
import com.mm.hamcompose.ui.route.RouteUtils.back
import com.mm.hamcompose.ui.widget.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/***
 * 分享
 * 文章
 */

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalPagerApi::class)
@Composable
fun MainPage(
    navCtrl: NavHostController,
    scaffoldState: ScaffoldState,
    mainViewModel:MainViewModel= hiltViewModel()
) {
    val currentPageIndex by mainViewModel.currentPageIndex.collectAsState()
    val pageState = rememberPagerState(
        pageCount = 4,
        initialPage = currentPageIndex
    )
    val scopeState = rememberCoroutineScope()

    val homeIndex by  mainViewModel.homeIndex.collectAsState()
    val categoryIndex by  mainViewModel.categoryIndex.collectAsState()
    //mainViewModel.currentPageIndex这个StateFlow<Int> 必须在协程中启动
//    mainViewModel.viewModelScope.launch {
//        mainViewModel.currentPageIndex.collect {value->
//
//        }
//    }
    //mainViewModel.myPageIndex这个MutableStateFlow<Int> 必须在协程中启动
//     mainViewModel.viewModelScope.launch {
//         mainViewModel.myPageIndex.collect {value->
//
//         }
//     }


    LaunchedEffect(pageState.currentPage) {
        // 当页面已经切换到新页面，立即更新导航状态
        mainViewModel.updatePageIndex(pageState.currentPage)
    }


    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        bottomBar = {
            BottomNavBar(navCtrl,currentPageIndex){
                mainViewModel.updatePageIndex(it)
                scopeState.launch {
                    pageState.animateScrollToPage(currentPageIndex)
                }
            }
        },
        content = {
            HorizontalPager(state =pageState,modifier = Modifier
                .fillMaxSize()
            ) { page->
                when(page){
                    0-> HomePage(navCtrl = navCtrl, scaffoldState = scaffoldState,homeIndex=homeIndex){ index->
                        mainViewModel.updateHomeIndex(index)
                    }
                    1-> CategoryPage(navCtrl = navCtrl,categoryIndex=categoryIndex){index->
                        mainViewModel.updateCategoryIndex(index)
                    }
                    2-> CollectionPage(navCtrl = navCtrl,scaffoldState =scaffoldState )
                    3-> ProfilePage(navCtrl = navCtrl)
                }
            }
        }
    )

}


