package com.mm.hamcompose.ui.widget

import android.widget.Space
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.placeholder.material.placeholder
import com.mm.hamcompose.R
import com.mm.hamcompose.data.bean.TabTitle
import com.mm.hamcompose.theme.*
import com.mm.hamcompose.ui.route.BottomNavRoute

/**
 * 普通标题栏头部
 */
@Composable
fun HamToolBar(
    title: String,
    rightText: String? = null,
    onBack: (() -> Unit)? = null,
    onRightClick: (() -> Unit)? = null,
    imageVector: ImageVector? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(ToolBarHeight)
            .background(HamTheme.colors.themeUi)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            if (onBack != null) {
                Icon(
                    Icons.Default.ArrowBack,
                    null,
                    Modifier
                        .clickable(onClick = onBack)
                        .align(Alignment.CenterVertically)
                        .padding(12.dp),
                    tint = HamTheme.colors.mainColor
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (!rightText.isNullOrEmpty() && imageVector == null) {
                TextContent(
                    text = rightText,
                    color = HamTheme.colors.mainColor,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 20.dp)
                        .clickable{ onRightClick?.invoke() }
                )
            }

            if (imageVector != null) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = HamTheme.colors.mainColor,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 12.dp)
                        .clickable {
                            onRightClick?.invoke()
                        })
            }
        }
        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 40.dp),
            color = HamTheme.colors.mainColor,
            textAlign = TextAlign.Center,
            fontSize = if (title.length > 14) H5 else ToolBarTitleSize,
            fontWeight = FontWeight.W500,
            maxLines = 1
        )

    }
}


@Composable
fun HomeSearchBar(
    onUserIconClick: ()-> Unit,
    onSearchClick: () -> Unit,
    onRightIconClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(SearchBarHeight)
            .background(color = HamTheme.colors.themeUi)
    ) {
        Image(
            painter = painterResource(id = R.drawable.wukong),
            contentDescription = "User",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 10.dp)//相当于margin
                .width(28.dp)
                .height(28.dp)
                .clip(RoundedCornerShape(14.dp))
                .align(alignment = Alignment.CenterVertically)  //在一行里面居中对齐
                .clickable {
                    onUserIconClick.invoke()
                }
        )
        //搜索框
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .height(25.dp)
                .align(alignment = Alignment.CenterVertically)
                .weight(1f)
                .background(//圆角
                    color = HamTheme.colors.mainColor,
                    shape = RoundedCornerShape(12.5.dp)
                )
                .clickable { onSearchClick() }
        ) {
            Spacer(modifier = Modifier.weight(1.0f).padding(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "搜索",
                tint = HamTheme.colors.themeUi,
                modifier = Modifier
                    .size(25.dp)
                    .padding(end = 10.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_menu_welfare),
            contentDescription = "抽屉",
            tint = white1,
            modifier = Modifier
                .size(34.dp)
                .align(alignment = Alignment.CenterVertically)
                .padding(end = 10.dp)
                .clickable {
                    onRightIconClick.invoke()
                }
        )
    }
}


/**
 * TabLayout
 */
@Composable
fun TextTabBar(
    index: Int,
    tabTexts: MutableList<TabTitle>,
    modifier: Modifier = Modifier,
    bgColor: Color = HamTheme.colors.themeUi,
    contentColor: Color = Color.White,
    onTabSelected: ((Int) -> Unit)? = null,
    withAdd: Boolean = false,
    onAddClick: () -> Unit = { },
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(TabBarHeight)
            .background(bgColor)
            .horizontalScroll(state = rememberScrollState())
    ) {
        tabTexts.forEachIndexed { i, tabTitle ->
            Text(
                text = tabTitle.text,
                fontSize = if (index == i) 20.sp else 15.sp,
                fontWeight = if (index == i) FontWeight.SemiBold else FontWeight.Normal,
                modifier = Modifier
                    .align(Alignment.CenterVertically) //在一行内部竖直居中
                    .padding(horizontal = 10.dp)
                    .clickable {
                        if (onTabSelected != null) {
                            onTabSelected(i)
                        }
                    },
                color = contentColor
            )
        }
        if (withAdd) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = HamTheme.colors.mainColor,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        onAddClick.invoke()
                    }
            )
        }
    }
}

@Composable
fun TabBar(
    index: Int = 0,
    tabTexts: List<String>,
    modifier: Modifier = Modifier,
    bgColor: Color,
    contentColor: Color,
    onTabSelected: ((index: Int) -> Unit)?,
    isScrollable: Boolean = true
) {
    if (isScrollable) {
        ScrollableTabRow(
            selectedTabIndex = index,
            modifier = modifier.height(TabBarHeight),
            edgePadding = 0.dp,
            backgroundColor = bgColor,
            contentColor = contentColor,
        ) {
            //var offset: Float by remember { mutableStateOf(0f) }
            tabTexts.forEachIndexed { i, tabText ->
                Text(
                    text = tabText,
                    fontSize = if (index == i) 20.sp else 15.sp,
                    fontWeight = if (index == i) FontWeight.SemiBold else FontWeight.Normal,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .clickable {
                            if (onTabSelected != null) {
                                onTabSelected(i)
                            }
                        },
                    color = contentColor
                )
            }
        }
    } else {
        TabRow(
            selectedTabIndex = index,
            modifier = modifier.height(TabBarHeight),
            backgroundColor = bgColor,
            contentColor = contentColor,
        ) {
            tabTexts.forEachIndexed { i, tabText ->
                Text(
                    text = tabText,
                    fontSize = if (index == i) 20.sp else 15.sp,
                    fontWeight = if (index == i) FontWeight.SemiBold else FontWeight.Normal,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .clickable {
                            if (onTabSelected != null) {
                                onTabSelected(i)
                            }
                        },
                    color = contentColor
                )
            }
        }
    }
}

@Composable
fun BottomNavBarView(navCtrl: NavHostController) {
    val bottomNavList = listOf(
        BottomNavRoute.Home,
        BottomNavRoute.Category,
        BottomNavRoute.Collection,
        BottomNavRoute.Profile
    )
    BottomNavigation {
        val navBackStackEntry by navCtrl.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        //生成每一个TabItemView
        bottomNavList.forEach { screen ->
            BottomNavigationItem(
                modifier = Modifier.background(HamTheme.colors.themeUi),
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = null
                    )
                },
                label = { Text(text = stringResource(screen.stringId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.routeName } == true,
                onClick = {
                    println("BottomNavView当前路由 ===> ${currentDestination?.hierarchy?.toList()}")
                    println("当前路由栈 ===> ${navCtrl.graph.nodes}")
                    if (currentDestination?.route != screen.routeName) {
                        //导航后清除起始页和目的页之间的页面
                        navCtrl.navigate(screen.routeName) {
                            //点击返回键直接返回Home首页，而不是一层一层返回
                           popUpTo(navCtrl.graph.findStartDestination().id) {
                                saveState = true
                               // inclusive=true
                            }
                            launchSingleTop = true //如果该页面在栈顶的话，不会重新入栈，而是会复用该栈
                            restoreState = true
                        }
                    }
                })
        }
    }
}

@Preview
@Composable
fun EmptyView(
    tips: String = "啥都没有~",
    imageVector: ImageVector = Icons.Default.Info,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .defaultMinSize(minHeight = 480.dp)
    ) {
        Column(
            Modifier
                .wrapContentSize()
                .align(Alignment.Center)
                .clickable { onClick.invoke() }
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = HamTheme.colors.textSecondary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            TextContent(text = tips, modifier = Modifier.padding(top = 10.dp))
        }
    }
}

@Composable
fun SwitchTabBar(
    titles: MutableList<TabTitle>,
    heightValue: Dp? = null,
    selectIndex: Int,
    onSwitchClick: (index: Int) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(heightValue ?: ToolBarHeight)
            .background(color = HamTheme.colors.themeUi)
            .padding(vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center)
        ) {
            titles.forEachIndexed { index, tabTitle ->
                MediumTitle(
                    title = tabTitle.text,
                    color = if (index == selectIndex) HamTheme.colors.textPrimary
                    else HamTheme.colors.textSecondary,
                    modifier = Modifier
                        .defaultMinSize(100.dp, 24.dp)
                        .padding(horizontal = 10.dp)
                        .clickable { onSwitchClick.invoke(index) },
                    textAlign = TextAlign.Center
                )
            }
        }
        //悬浮布局在中间位置
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(16.dp)
                .background(color = HamTheme.colors.textSecondary)
                .align(Alignment.Center)
        )
    }
}


@Composable
fun TagView(
    modifier: Modifier = Modifier,
    tagText: String,
    tagBgColor: Color = HamTheme.colors.background,
    borderColor: Color = HamTheme.colors.themeUi,
    tagTextColor: Color = HamTheme.colors.textSecondary,
    isLoading: Boolean = false,
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .background(color = tagBgColor)
            .clip(RoundedCornerShape(2.dp))
            .border(width = 1.dp, color = borderColor)
            .placeholder(
                visible = isLoading,
                color = HamTheme.colors.placeholder
            )
    ) {
        MiniTitle(
            text = tagText,
            color = tagTextColor,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 5.dp, vertical = 0.dp),
        )
    }
}


