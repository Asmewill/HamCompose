package com.example.owapp.page

import android.graphics.Paint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.R
import com.example.owapp.ui.theme.*
import com.example.owapp.util.Constant
import com.example.owapp.util.HamToolBar1
import com.example.owapp.viewmodel.SettingViewModel

/**
 * Created by Owen on 2023/5/25
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingsPage(navCtrl: NavHostController) {
    val settingViewModel:SettingViewModel = viewModel()
    val isLogout=settingViewModel.logoutLiveData.observeAsState().value
    var isExitApp by remember { mutableStateOf(false) }
    var isAboutMe by remember { mutableStateOf(false)}
    var isClearCache by remember { mutableStateOf(false) }
    var isSelectTheme by remember { mutableStateOf(false)}
    var selectIndex by remember { mutableStateOf(-1)}
    isLogout?.let {
        if(it){
            navCtrl.navigateUp()
            SPUtils.getInstance().put(Constant.IS_LOGIN,false)
        }
    }
    if (isExitApp) {
        AlertDialog(
            onDismissRequest = {
                isExitApp = false
            },
            confirmButton = {
                TextButton(onClick = {
                    isExitApp = false
                    settingViewModel.logout()

                }) {
                    Text(text = "确定", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    isExitApp = false
                }) {
                    Text(text = "取消", color = Color.Black)
                }
            },
            title={
                Text(text = "提示")
            },
            text = {
                Text(text="退出后，将无法查看我的文章，消息，收藏，积分，浏览记录等功能，确定退出登录吗?")
            },
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
        )
    }
    if(isAboutMe){
        AlertDialog(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            title={
                Text(text = "提示")
            },
            text = {
                   Column() {
                       Text(text = "作者:Asmewill", fontSize = 14.sp,color=Color.Gray,modifier = Modifier.padding(top = 10.dp))
                       Text(text = "email:jsxiaoshui@gmail.com", fontSize = 14.sp,color=Color.Gray, modifier = Modifier.padding(top = 10.dp))
                       Text(text = "版本号:V1.0", fontSize = 14.sp,color=Color.Gray, modifier = Modifier.padding(top = 10.dp))
                       Text(text = "项目源码:", fontSize = 14.sp,color=Color.Gray, modifier = Modifier.padding(top = 10.dp))
                       Text(text = "https://github.com/Asmewill/HamCompose.git", fontSize = 14.sp,color=Color.Gray)
                       Text(text = "版权声明:本app仅用于学习用处，不得抄袭用于商业行为", fontSize = 14.sp,color=Color.Gray, modifier = Modifier.padding(top = 10.dp))
                   }
            },
            onDismissRequest = {
                isAboutMe = false
            },
            confirmButton = {
                TextButton(onClick = {
                    isAboutMe = false
                }) {
                    Text(text = "关闭", color = Color.Black)
                }
            }
        )
    }
    if(isClearCache){
        AlertDialog(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            title={
                Text(text = "提示")
            },
            text = {
                Column() {
                    Text(text = "清除缓存后，缓存文件夹中的照片和文件可能丢失，确定要清除吗?", fontSize = 14.sp,color=Color.Gray,modifier = Modifier.padding(top = 10.dp))
                }
            },
            onDismissRequest = {
                isClearCache = false
            },
            confirmButton = {
                TextButton(onClick = {
                    isClearCache = false
                }) {
                    Text(text = "关闭", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    isClearCache=false
                }) {
                    Text(text = "取消",color=Color.Gray)
                }
            }
        )
    }
    if(isSelectTheme){
        AlertDialog(
            modifier=Modifier.defaultMinSize(minWidth = 300.dp),
            onDismissRequest = {
                isSelectTheme=false
            },
            title = {
                Text(text = "选择主题")
            },
            text = {
                LazyVerticalGrid(cells = GridCells.Fixed(4), content = {
                    itemsIndexed(themeColors){index,color->
                       Box(modifier= Modifier
                           .padding(5.dp)
                           .wrapContentSize()
                           .background(color = Color.White, shape = RoundedCornerShape(24.dp)), contentAlignment = Alignment.Center) {
                           Box(modifier= Modifier
                               .padding(5.dp)
                               .size(48.dp)
                               .clip(RoundedCornerShape(24.dp))
                               .background(color = color)
                               .clickable {
                                   selectIndex = index
                               }, contentAlignment = Alignment.Center) {
                               if(selectIndex==index){
                                   Icon(imageVector = Icons.Default.Done, contentDescription = "", tint = Color.White)
                               }
                           }
                       }
                    }
                })
            },
            confirmButton = {
                TextButton(onClick = {
                    isSelectTheme=false
                    selectIndex=-1
                }) {
                    Text(text = "确定",color=Color.Black)
                }
            }
        )
    }

    Column(Modifier.fillMaxSize()) {
        HamToolBar1(middleTitle = "设置", onBack = {
            navCtrl.navigateUp()
        })
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .background(white1)
            .clickable {
                isSelectTheme = true
            }
            .padding(start = 10.dp, end = 10.dp),//此处是正正的padding
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_theme),
                contentDescription = "",
                tint = grey5,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = "主题切换",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "",
                tint = Color.Gray
            )
        }
        Divider(
            Modifier
                .height(1.dp)
                .background(color = white3)
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .background(white1)
            .clickable {
                isClearCache = true
            }
            .padding(start = 10.dp, end = 10.dp),//此处是正正的padding
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_message),
                contentDescription = "",
                tint = grey5,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = "清理缓存",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "0.00M",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(end = 10.dp)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "",
                tint = Color.Gray
            )
        }
        Divider(
            Modifier
                .height(1.dp)
                .background(color = white3)
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .background(white1)
            .clickable {
                isAboutMe = true
            }
            .padding(start = 10.dp, end = 10.dp),//此处是正正的padding
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_help),
                contentDescription = "",
                tint = grey5,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = "关于我",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "",
                tint = Color.Gray
            )
        }
        Divider(
            Modifier
                .height(1.dp)
                .background(color = white3)
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .background(white1)
            .clickable {
                isExitApp = true
            }
            .padding(start = 10.dp, end = 10.dp),//此处是正正的padding
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_message),
                contentDescription = "",
                tint = grey5,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = "退出登录",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "",
                tint = Color.Gray
            )
        }
        Divider(
            Modifier
                .height(1.dp)
                .background(color = white3)
        )
    }
}