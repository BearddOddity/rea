package dev.daybreak.os.input

/** Device-independent navigation actions, so remote, gamepad and keyboard behave identically. */
enum class NavAction { UP, DOWN, LEFT, RIGHT, SELECT, BACK, SWITCH_DASHBOARD, MENU }

/**
 * Maps raw Android keycodes to [NavAction]s. Kept free of android.* types
 * (keycodes are plain ints) so it is unit-testable on the JVM.
 */
object InputMapper {
    // Values from android.view.KeyEvent
    private const val DPAD_UP = 19
    private const val DPAD_DOWN = 20
    private const val DPAD_LEFT = 21
    private const val DPAD_RIGHT = 22
    private const val DPAD_CENTER = 23
    private const val ENTER = 66
    private const val BACK = 4
    private const val MENU = 82
    private const val ALT_LEFT = 57
    private const val ALT_RIGHT = 58
    private const val BUTTON_A = 96
    private const val BUTTON_B = 97
    private const val BUTTON_Y = 100
    private const val BUTTON_START = 108
    private const val BUTTON_SELECT = 109

    fun map(keyCode: Int): NavAction? = when (keyCode) {
        DPAD_UP -> NavAction.UP
        DPAD_DOWN -> NavAction.DOWN
        DPAD_LEFT -> NavAction.LEFT
        DPAD_RIGHT -> NavAction.RIGHT
        DPAD_CENTER, ENTER, BUTTON_A -> NavAction.SELECT
        BACK, BUTTON_B -> NavAction.BACK
        ALT_LEFT, ALT_RIGHT, BUTTON_Y, BUTTON_SELECT -> NavAction.SWITCH_DASHBOARD
        MENU, BUTTON_START -> NavAction.MENU
        else -> null
    }
}
