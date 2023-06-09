package com.example.owapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.owapp.route.BottomNavRoute
import com.example.owapp.ui.theme.HamComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HamComposeTheme {
                HomeEntry {
                   finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //保证退出后，初始化的Tab一直是Home
      //  bottomNavRoute.value=BottomNavRoute.Home
    }
}

