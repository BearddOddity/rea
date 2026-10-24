package dev.daybreak.os.theme

enum class LayoutStyle {
    /** Left rail + library grid (SteamOS Gaming Mode). */
    SIDEBAR_GRID,
    /** Single horizontal row of large tiles (PS5, Switch). */
    TILE_ROW,
}

/** Colors are ARGB longs so this file stays free of Compose/android imports. */
data class DashboardTheme(
    val id: String,
    val displayName: String,
    val layout: LayoutStyle,
    val background: Long,
    val surface: Long,
    val accent: Long,
    val onBackground: Long,
    val cornerRadiusDp: Int,
)

object Themes {
    val steamDeck = DashboardTheme("deck", "Deck", LayoutStyle.SIDEBAR_GRID,
        0xFF0E141B, 0xFF1A2430, 0xFF1A9FFF, 0xFFDCDEDF, 6)
    val xbox360 = DashboardTheme("x360", "Xbox 360", LayoutStyle.TILE_ROW,
        0xFFF2F2F2, 0xFFFFFFFF, 0xFF7AB800, 0xFF2B2B2B, 4)
    val ps5 = DashboardTheme("ps5", "PS5", LayoutStyle.TILE_ROW,
        0xFF0B1226, 0xFF16204A, 0xFFFFFFFF, 0xFFE8ECF8, 14)
    val switch2 = DashboardTheme("switch2", "Switch 2", LayoutStyle.TILE_ROW,
        0xFF2D2D2D, 0xFF3A3A3A, 0xFFE60012, 0xFFFFFFFF, 20)

    val all = listOf(steamDeck, xbox360, ps5, switch2)

    fun next(current: DashboardTheme): DashboardTheme =
        all[(all.indexOf(current) + 1) % all.size]

    fun byId(id: String?): DashboardTheme = all.firstOrNull { it.id == id } ?: steamDeck
}
