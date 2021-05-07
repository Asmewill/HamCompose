package com.mm.hamcompose.ui.page.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mm.hamcompose.R
import com.mm.hamcompose.bean.ParentBean
import com.mm.hamcompose.theme.HamTheme
import com.mm.hamcompose.theme.green1
import com.mm.hamcompose.theme.white1
import com.mm.hamcompose.ui.HamRouter
import com.mm.hamcompose.ui.RouteActions
import com.mm.hamcompose.ui.page.index.IndexPage
import com.mm.hamcompose.ui.page.navigation.NaviPage
import com.mm.hamcompose.ui.page.project.hot.ProjectPage
import com.mm.hamcompose.ui.page.square.SquarePage
import com.mm.hamcompose.ui.page.subscription.category.SubscriptionPage
import com.mm.hamcompose.ui.page.system.tree.SystemPage
import com.mm.hamcompose.ui.page.wenda.WenDaPage
import com.mm.hamcompose.ui.widget.MediumTitle
import com.mm.hamcompose.ui.widget.TextContent
import com.mm.hamcompose.ui.widget.TopTabBar
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomePage(
    homeIndex: Int = 0,
    onPageSelected: (position: Int) -> Unit,
    actions: RouteActions
) {
    Scaffold(
        modifier = Modifier.background(HamTheme.colors.background),
        content = {
            Column {

                SearchBar(
                    onSearchClick = { actions.selected(HamRouter.articleSearch, null) },
                    onGirlClick = {actions.selected(HamRouter.girlPhoto, null)}
                )

                val titles = mutableListOf("首页", "广场", "知识体系", "导航", "公众号", "项目", "问答")
                val pagerState = rememberPagerState(titles.size, homeIndex)
                val scopeState = rememberCoroutineScope()

                TopTabBar(
                    index = pagerState.currentPage,
                    tabTexts = titles,
                ) { index ->
                    scopeState.launch {
                        pagerState.scrollToPage(index)
                    }
                }

                HorizontalPager(state = pagerState) { page ->
                    //LogUtils.e("当前HomeIndex = ${pagerState.currentPage}")
                    onPageSelected(pagerState.currentPage)
                    when (page) {
                        0 -> IndexPage(onSelected = actions.selected)
                        1 -> SquarePage(actions = actions)
                        2 -> SystemPage(onSelected = actions.selected)
                        3 -> NaviPage(onSelected = actions.selected)
                        4 -> SubscriptionPage(onSelected = actions.selected)
                        5 -> ProjectPage(actions = actions)
                        6 -> WenDaPage(actions = actions)
                    }
                }
            }
        }
    )
}

@Composable
fun SearchBar(onSearchClick: () -> Unit, onGirlClick: ()-> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(color = HamTheme.colors.themeUi)
    ) {
        //搜索框
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .height(25.dp)
                .align(alignment = Alignment.Bottom)
                .weight(1f)
                .background(
                    color = white1,
                    shape = RoundedCornerShape(12.5.dp)
                )
                .clickable { onSearchClick() }
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 5.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "搜索",
                tint = HamTheme.colors.themeUi,
                modifier = Modifier
                    .size(25.dp)
                    .padding(horizontal = 5.dp)
                    .align(Alignment.CenterVertically)
            )

        }
        //看妹子
        Row(
            modifier = Modifier
                .padding(end = 10.dp, bottom = 4.dp)
                .align(alignment = Alignment.Bottom)
                .clickable { onGirlClick() }
        ) {
            MediumTitle(
                title = "妹纸",
                color = white1,
                modifier = Modifier.align(alignment = Alignment.Bottom)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_more),
                contentDescription = "看妹纸",
                tint = white1,
                modifier = Modifier
                    .width(15.dp)
                    .height(18.dp)
                    .align(alignment = Alignment.Bottom)
                    .padding(bottom = 2.dp)

            )
        }
    }
}