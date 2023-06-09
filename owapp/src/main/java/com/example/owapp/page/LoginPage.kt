package com.example.owapp.page

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.owapp.R
import com.example.owapp.route.RouteName
import com.example.owapp.ui.theme.c_B3F
import com.example.owapp.util.Constant
import com.example.owapp.viewmodel.LoginViewModel


/**
 * Created by Owen on 2023/5/24
 */
@SuppressLint("UnrememberedMutableState")
@Composable
fun LoginPage(navCtrl:NavHostController){
      val loginViewModel:LoginViewModel = viewModel()
      val userName= mutableStateOf(TextFieldValue(""))
      val pwd= mutableStateOf(TextFieldValue(""))
      val pwdVisible= mutableStateOf(false)
      val loginData= loginViewModel.loginLiveData.observeAsState().value
      //登录成功跳转
      loginData?.let {
            SPUtils.getInstance().put(Constant.IS_LOGIN,true)
            SPUtils.getInstance().put(Constant.LOGIN_BEAN, GsonUtils.toJson(it))
            navCtrl.navigateUp()
      }
      Box(modifier = Modifier.fillMaxSize()){
            Image(painter = painterResource(id = R.mipmap.login_bg),
                  contentDescription = "",
                  contentScale = ContentScale.FillBounds,
                  modifier = Modifier.fillMaxSize()
            )
            LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
                  item {
                        //TopBar
                        Row(modifier= Modifier
                              .padding(start = 15.dp, end = 15.dp)
                              .fillMaxWidth()
                              .height(50.dp),
                              horizontalArrangement = Arrangement.SpaceBetween,
                              verticalAlignment = Alignment.CenterVertically
                        ) {
                              Icon(painter = painterResource(id = R.mipmap.ic_tab_strip_icon_follow),
                                    contentDescription ="", tint = Color.White, modifier = Modifier
                                          .size(25.dp)
                                          .clickable {
                                                navCtrl.navigateUp()
                                          })
                              Text(text = "跳过", fontSize = 12.sp, color= Color.White,modifier=Modifier.clickable {
                                    navCtrl.navigateUp()
                              })
                        }

                        //MiddleContent
                        Row(horizontalArrangement = Arrangement.Center,
                              modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 50.dp)) {
                              Image(painter = painterResource(id = R.mipmap.ic_account),
                                    contentDescription ="",modifier=Modifier.size(100.dp))
                        }

                        //UserName
                        OutlinedTextField(
                              modifier = Modifier
                                    .padding(start = 50.dp, end = 50.dp, top = 40.dp)
                                    .fillMaxWidth()
                                    .height(56.dp),
                              value =userName.value ,
                              singleLine = true,
                              onValueChange ={
                                  userName.value=it
                              },
                              label={
                                    Text(text = "请输入手机号/邮箱地址", fontSize = 12.sp)
                              },
                              textStyle = TextStyle(
                                    fontSize = 12.sp
                              ),
                              leadingIcon = {
                                    Icon(painter = painterResource(id = R.mipmap.ic_account_login_user_name), contentDescription ="")
                              },
                              colors=TextFieldDefaults.textFieldColors(
                                    unfocusedLabelColor = Color.White,
                                    textColor = Color.White,
                                    backgroundColor = Color.Transparent,
                                    focusedLabelColor = Color.White,
                                    leadingIconColor = Color.White,
                                    focusedIndicatorColor = Color.White,
                                    unfocusedIndicatorColor = Color.White,
                                    //光标
                                    cursorColor = Color.White
                              )
                        )

                        //PassWord
                        OutlinedTextField(
                              modifier = Modifier
                                    .padding(start = 50.dp, end = 50.dp, top = 18.dp)
                                    .fillMaxWidth()
                                    .height(56.dp),
                              value =pwd.value ,
                              singleLine = true,
                              onValueChange ={
                                    pwd.value=it
                              },
                              label={
                                    Text(text = "请输入密码", fontSize = 12.sp)
                              },
                              textStyle = TextStyle(
                                    fontSize = 12.sp
                              ),
                              leadingIcon = {
                                    Icon(painter = painterResource(id = R.mipmap.ic_account_login_password), contentDescription ="")
                              },
                              colors=TextFieldDefaults.textFieldColors(
                                    unfocusedLabelColor = Color.White,
                                    textColor = Color.White,
                                    backgroundColor = Color.Transparent,
                                    focusedLabelColor = Color.White,
                                    leadingIconColor = Color.White,
                                    focusedIndicatorColor = Color.White,
                                    unfocusedIndicatorColor = Color.White,
                                    //光标
                                    cursorColor = Color.White
                              ),
                              keyboardOptions= KeyboardOptions(keyboardType= KeyboardType.Password),
                              trailingIcon={
                                    IconButton(onClick = {
                                          pwdVisible.value=!pwdVisible.value
                                    }) {
                                          if(pwdVisible.value){
                                                Icon(
                                                      painter = painterResource(id = R.mipmap.ic_password_view),
                                                      contentDescription ="" ,
                                                      tint =Color.White, modifier = Modifier.size(20.dp))
                                          }else{
                                                Icon(
                                                      painter = painterResource(id = R.mipmap.ic_password_view_off),
                                                      contentDescription ="",
                                                      tint =Color.White, modifier = Modifier.size(20.dp))
                                          }
                                    }
                              },
                              //密码是否可见
                              visualTransformation = if(pwdVisible.value) VisualTransformation.None  else PasswordVisualTransformation()
                        )

                        //登录按钮  用户注册  作者登录
                        Button(onClick = {
                              loginViewModel.toLogin(userName = userName.value.text, pwd = pwd.value.text)
                        },modifier= Modifier
                              .padding(start = 50.dp, end = 50.dp, top = 20.dp)
                              .fillMaxWidth(),colors = ButtonDefaults.buttonColors(
                              contentColor=Color.Black,
                              backgroundColor= c_B3F,
                        )) {
                           Text(text = "登录")
                        }
                        //用户注册  找回密码
                        Row(modifier= Modifier
                              .padding(start = 50.dp, end = 50.dp, top = 40.dp)
                              .fillMaxWidth(),
                              horizontalArrangement = Arrangement.SpaceEvenly) { //平均分配
                              Text(text = "用户注册",color=Color.White, modifier = Modifier.clickable {
                                     navCtrl.navigate(RouteName.REGISTER)
                              })
                              Text(text = "找回密码",color=Color.White)
                        }
                      //第三方登录
                        Row(modifier= Modifier
                              .padding(start = 50.dp, end = 50.dp, top = 90.dp)
                              .fillMaxWidth(),
                            horizontalArrangement=Arrangement.SpaceEvenly){
                              Box(modifier= Modifier
                                    .background(
                                          color = Color.White,
                                          shape = RoundedCornerShape(25.dp)
                                    )
                                    .size(50.dp)
                                    .clickable {
                                          ToastUtils.showLong("WeChat")
                                    }, contentAlignment = Alignment.Center){
                                    Icon(
                                          painter = painterResource(id = R.mipmap.ic_action_share_wechat_grey),
                                          contentDescription = "",
                                    )
                              }
                              Box(modifier= Modifier
                                    .background(
                                          color = Color.White,
                                          shape = RoundedCornerShape(25.dp)
                                    )
                                    .size(50.dp)
                                    .clickable {
                                          ToastUtils.showLong("WeiBo")
                                    }, contentAlignment = Alignment.Center){
                                    Icon(
                                          painter = painterResource(id = R.mipmap.ic_action_share_weibo_grey),
                                          contentDescription = "",
                                    )
                              }
                              Box(modifier= Modifier
                                    .background(
                                          color = Color.White,
                                          shape = RoundedCornerShape(25.dp)
                                    )
                                    .size(50.dp)
                                    .clickable {
                                          ToastUtils.showLong("QQ")
                                    }, contentAlignment = Alignment.Center){
                                    Icon(
                                          painter = painterResource(id = R.mipmap.ic_action_share_qq_grey),
                                          contentDescription = "",
                                    )
                              }
                        }
                  }
            }
      }

}