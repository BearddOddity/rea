package dev.daybreak.os

import dev.daybreak.os.input.InputMapper
import dev.daybreak.os.input.NavAction
import dev.daybreak.os.theme.Themes
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CoreTest {
    @Test fun gamepadAndRemoteMapToSameActions() {
        assertEquals(NavAction.SELECT, InputMapper.map(96)) // BUTTON_A
        assertEquals(NavAction.SELECT, InputMapper.map(23)) // DPAD_CENTER (remote)
        assertEquals(NavAction.SWITCH_DASHBOARD, InputMapper.map(100)) // BUTTON_Y
        assertEquals(NavAction.SWITCH_DASHBOARD, InputMapper.map(57)) // ALT_LEFT
        assertNull(InputMapper.map(29))
    }

    @Test fun themeCycleWraps() {
        var t = Themes.steamDeck
        repeat(Themes.all.size) { t = Themes.next(t) }
        assertEquals(Themes.steamDeck, t)
    }
}
