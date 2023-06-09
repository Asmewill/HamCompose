package com.example.owapp.route

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.owapp.R

/**
 * Created by Owen on 2023/5/19
 */
sealed  class BottomNavRoute(val routeName:String,val stringId:Int,val mipId:Int) {
    object Home:BottomNavRoute(RouteName.HOME, R.string.home,R.mipmap.nav_home)
    object Category:BottomNavRoute(RouteName.CATEGORY, R.string.category,R.mipmap.nav_category)
    object Project:BottomNavRoute(RouteName.PROJECT, R.string.project,R.mipmap.nav_project)
    object Mine:BottomNavRoute(RouteName.MINE, R.string.mine,R.mipmap.nav_mine)
}

//enum Season {
//    // 枚举定义的常量对象必须在最前面
//    SPRING("春天","万物复苏"),
//    SUMMER("夏天","烈日炎炎"),
//    AUTUMN("秋天","硕果累累"),
//    WINTER("冬天","寒冷刺骨");
//
//    private String name;
//    private String desc;
//
//    private Season(String name, String desc) {
//        this.name = name;
//        this.desc = desc;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getDesc() {
//        return desc;
//    }
//
//    @Override
//    public String toString() {
//        return "Season{" +
//                "name='" + name + '\'' +
//                ", desc='" + desc + '\'' +
//                '}';
//    }
//}