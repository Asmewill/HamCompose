package com.mm.hamcompose.ui.route

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.mm.hamcompose.R


sealed class BottomNavRoute(
    var routeName: String,
    @StringRes var stringId: Int,
    var icon: ImageVector
) {
    object Home: BottomNavRoute(RouteName.HOME, R.string.home, Icons.Default.Home)
    object Category: BottomNavRoute(RouteName.CATEGORY, R.string.category, Icons.Default.Menu)
    object Collection: BottomNavRoute(RouteName.COLLECTION, R.string.collection, Icons.Default.Favorite)
    object Profile: BottomNavRoute(RouteName.PROFILE, R.string.profile, Icons.Default.Person)
}




sealed  class BottomNavRouteNew(val routeName:String,val stringId:Int,val mipId:Int) {
    object Home:BottomNavRouteNew(RouteName.HOME, R.string.home, R.mipmap.nav_home)
    object Category:BottomNavRouteNew(RouteName.CATEGORY, R.string.category,R.mipmap.nav_category)
    object Project:BottomNavRouteNew(RouteName.COLLECTION, R.string.collection,R.mipmap.nav_project)
    object Mine:BottomNavRouteNew(RouteName.PROFILE, R.string.profile,R.mipmap.nav_mine)
}


//enum class BottomNavRoute(
//    var routeName: String,
//    @StringRes var stringId: Int,
//    var icon: ImageVector
//) {
//    Home(RouteName.HOME, R.string.home, Icons.Default.Home),
//    Category(RouteName.CATEGORY, R.string.category, Icons.Default.Menu),
//    Collection(RouteName.COLLECTION, R.string.collection, Icons.Default.Favorite),
//    Profile(RouteName.PROFILE, R.string.profile, Icons.Default.Person)
//}