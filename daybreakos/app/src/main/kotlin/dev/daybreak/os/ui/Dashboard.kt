package dev.daybreak.os.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import dev.daybreak.os.apps.TvApp
import dev.daybreak.os.theme.DashboardTheme
import dev.daybreak.os.theme.LayoutStyle
import dev.daybreak.os.theme.Themes
import androidx.compose.foundation.Image

@Composable
fun Dashboard(
    theme: DashboardTheme,
    apps: List<TvApp>,
    onLaunch: (TvApp) -> Unit,
    onPickTheme: (DashboardTheme) -> Unit,
) {
    val bg = Color(theme.background)
    Box(Modifier.fillMaxSize().background(bg)) {
        when (theme.layout) {
            LayoutStyle.SIDEBAR_GRID -> SidebarGrid(theme, apps, onLaunch, onPickTheme)
            LayoutStyle.TILE_ROW -> TileRow(theme, apps, onLaunch, onPickTheme)
        }
    }
}

@Composable
private fun SidebarGrid(theme: DashboardTheme, apps: List<TvApp>, onLaunch: (TvApp) -> Unit, onPick: (DashboardTheme) -> Unit) {
    Row(Modifier.fillMaxSize().padding(32.dp), horizontalArrangement = Arrangement.spacedBy(32.dp)) {
        Column(Modifier.width(220.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("DAYBREAK", color = Color(theme.accent), fontSize = 20.sp)
            Text("Dashboards", color = Color(theme.onBackground), fontSize = 14.sp)
            Themes.all.forEach { t -> Tile(theme, t.displayName, null, Modifier.fillMaxWidth().height(48.dp)) { onPick(t) } }
        }
        LazyVerticalGrid(GridCells.Adaptive(180.dp), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(apps) { app -> AppTile(theme, app, Modifier.height(110.dp)) { onLaunch(app) } }
        }
    }
}

@Composable
private fun TileRow(theme: DashboardTheme, apps: List<TvApp>, onLaunch: (TvApp) -> Unit, onPick: (DashboardTheme) -> Unit) {
    Column(Modifier.fillMaxSize().padding(48.dp), verticalArrangement = Arrangement.SpaceBetween) {
        Text(theme.displayName, color = Color(theme.accent), fontSize = 28.sp)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(20.dp), verticalAlignment = Alignment.CenterVertically) {
            items(apps) { app -> AppTile(theme, app, Modifier.size(240.dp, 140.dp)) { onLaunch(app) } }
        }
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(Themes.all) { t -> Tile(theme, t.displayName, null, Modifier.size(140.dp, 44.dp)) { onPick(t) } }
        }
    }
}

@Composable
private fun AppTile(theme: DashboardTheme, app: TvApp, modifier: Modifier, onClick: () -> Unit) =
    Tile(theme, app.label, app, modifier, onClick)

@Composable
private fun Tile(theme: DashboardTheme, label: String, app: TvApp?, modifier: Modifier, onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(theme.cornerRadiusDp.dp)
    Box(
        modifier
            .clip(shape)
            .background(Color(theme.surface))
            .border(if (focused) 3.dp else 0.dp, Color(theme.accent), shape)
            .onFocusChanged { focused = it.isFocused }
            .clickable(onClick = onClick) // clickable is focusable and handles DPAD_CENTER/ENTER/BUTTON_A
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        val icon = app?.icon
        if (icon != null) {
            Image(icon.toBitmap(160, 90).asImageBitmap(), label, Modifier.fillMaxSize())
        } else {
            Text(label, color = Color(theme.onBackground), fontSize = 16.sp)
        }
    }
}
