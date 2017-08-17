package org.ice1000.mgg

import org.frice.game.utils.data.Preference
import org.frice.game.utils.time.Clock


fun MusicGame.player() {
	Preference("save.db").let { database ->
		addKeyListener(pressed = { if (it.keyCode in KEYS) database.insert("${Clock.current}", "${KEYS[it.keyCode]}") })
	}
}
