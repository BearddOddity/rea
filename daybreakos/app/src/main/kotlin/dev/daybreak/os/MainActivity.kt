package dev.daybreak.os

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import dev.daybreak.os.apps.AppRepository
import dev.daybreak.os.input.InputMapper
import dev.daybreak.os.input.NavAction
import dev.daybreak.os.theme.DashboardTheme
import dev.daybreak.os.theme.Themes
import dev.daybreak.os.ui.Dashboard

class MainActivity : ComponentActivity() {
    private val prefs by lazy { getSharedPreferences("daybreak", Context.MODE_PRIVATE) }
    private var theme by mutableStateOf(Themes.steamDeck)
    private var apps by mutableStateOf(emptyList<dev.daybreak.os.apps.TvApp>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme = Themes.byId(prefs.getString("theme", null))
        setContent {
            Dashboard(theme, apps, onLaunch = { startActivity(it.launch) }, onPickTheme = ::setTheme)
        }
    }

    override fun onResume() {
        super.onResume()
        apps = AppRepository(this).load() // refresh after installs/uninstalls
    }

    private fun setTheme(t: DashboardTheme) {
        theme = t
        prefs.edit().putString("theme", t.id).apply()
    }

    // Only intercept the dashboard-switch action; D-pad focus and select are handled by Compose.
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.repeatCount == 0 && InputMapper.map(keyCode) == NavAction.SWITCH_DASHBOARD) {
            setTheme(Themes.next(theme))
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    // As the Home app, Back must not exit.
    @Deprecated("Home screen swallows Back")
    override fun onBackPressed() = Unit
}
