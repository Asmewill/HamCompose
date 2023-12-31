package com.mm.hamcompose.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.material.placeholder
import com.mm.hamcompose.R
import com.mm.hamcompose.theme.HamTheme
import com.mm.hamcompose.theme.white

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HotIcon(
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_hot),
        contentDescription = null,
        tint = HamTheme.colors.hot,
        modifier = modifier
            .size(20.dp)
            .pointerInteropFilter { false }
    )

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShareIcon(
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_share),
        contentDescription = null,
        modifier = modifier
            .width(25.dp)
            .height(25.dp)
            .pointerInteropFilter { false }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FavouriteIcon(
    modifier: Modifier = Modifier,
    isFavourite: Boolean = false,
    onClick: () -> Unit,
    isLoading: Boolean = false
) {
    Icon(
        imageVector = if (isFavourite && !isLoading) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
        contentDescription = null,
        tint = if (isFavourite && !isLoading) HamTheme.colors.themeUi else HamTheme.colors.textSecondary,
        modifier = modifier
            .width(25.dp)
            .height(25.dp)
            .clickable(enabled = !isLoading) { onClick.invoke() }
            .pointerInteropFilter { false }
    )
}

@Composable
fun TimerIcon(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_time),
        contentDescription = "",
        tint = HamTheme.colors.textSecondary,
        modifier = modifier
            .width(15.dp)
            .height(15.dp)
            .clip(RoundedCornerShape(15.dp / 2))
            .placeholder(
                visible = isLoading,
                color = HamTheme.colors.placeholder
            )
    )
}

@Composable
fun UserIcon(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_author),
        contentDescription = "",
        modifier = modifier
            .width(15.dp)
            .height(15.dp)
            .clip(RoundedCornerShape(15.dp / 2))
            .placeholder(
                visible = isLoading,
                color = HamTheme.colors.placeholder
            ),
        tint = HamTheme.colors.textSecondary
    )
}

@Composable
fun AddIcon(
    modifier: Modifier,
    color: Color = HamTheme.colors.textPrimary
) {
    Icon(
        imageVector = Icons.Default.Add,
        contentDescription = null,
        tint = color,
        modifier = modifier
    )
}

@Composable
fun NotificationIcon(
    modifier: Modifier,
    tintColor: Color = white
) {
    Icon(
        Icons.Default.Notifications,
        contentDescription = "New message",
        modifier = modifier,
        tint = tintColor
    )
}

@Composable
fun DotView(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.size(10.dp).background(color=HamTheme.colors.hot, RoundedCornerShape(5.dp))){
        Text(
            text = "0",
            color = white,
            textAlign = TextAlign.Justify,
            maxLines = 1,
            fontSize = 5.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DeleteIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Icon(
        imageVector = Icons.Default.Delete,
        contentDescription = null,
        tint = HamTheme.colors.textSecondary,
        modifier = modifier
            .clickable {
                onClick.invoke()
            }
            .pointerInteropFilter { false }
    )
}