package org.ice1000.mgg

import org.frice.game.utils.data.Preference
import org.frice.game.utils.time.Clock

fun MusicGame.master() = Preference("save.db").let { database ->
	addKeyListener(pressed = { if (it.keyCode in KEYS) database.insert("${Clock.current - 1500}", "${KEYS[it.keyCode]}") })
}
