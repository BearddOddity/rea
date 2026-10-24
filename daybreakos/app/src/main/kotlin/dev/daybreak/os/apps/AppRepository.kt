package dev.daybreak.os.apps

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable

data class TvApp(val packageName: String, val label: String, val icon: Drawable?, val launch: Intent)

/** Lists everything installable on Google TV (Netflix, YouTube, Plex, emulators...) that has a TV launcher entry. */
class AppRepository(private val context: Context) {
    fun load(): List<TvApp> {
        val pm = context.packageManager
        val seen = HashSet<String>()
        return listOf(Intent.CATEGORY_LEANBACK_LAUNCHER, Intent.CATEGORY_LAUNCHER)
            .flatMap { category ->
                pm.queryIntentActivities(Intent(Intent.ACTION_MAIN).addCategory(category), 0)
            }
            .filter { it.activityInfo.packageName != context.packageName }
            .filter { seen.add(it.activityInfo.packageName) }
            .map { ri ->
                val pkg = ri.activityInfo.packageName
                TvApp(
                    pkg,
                    ri.loadLabel(pm).toString(),
                    ri.activityInfo.loadBanner(pm) ?: ri.loadIcon(pm),
                    pm.getLeanbackLaunchIntentForPackage(pkg) ?: pm.getLaunchIntentForPackage(pkg)
                        ?: Intent(Intent.ACTION_MAIN).setPackage(pkg),
                )
            }
            .sortedBy { it.label.lowercase() }
    }
}
