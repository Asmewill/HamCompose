package com.example.owapp.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

/**
 * Created by Owen on 2023/5/25
 */
@Composable
fun StudyPage(navCtr:NavHostController) {
    Column(Modifier.fillMaxSize()) {
        Text(text = "StudyPage")
    }
}