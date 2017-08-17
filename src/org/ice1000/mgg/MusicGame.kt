package org.ice1000.mgg

import org.frice.game.Game
import org.frice.game.utils.audio.play
import org.frice.game.utils.data.string2File
import org.frice.game.utils.message.FDialog
import org.frice.game.utils.time.FTimeListener

class MusicGame : Game(3) {
	override fun onInit() {
		setSize(600, 600)
		title = "ice1000's music game"
		autoGC = true
		"".string2File("save.db")
		when (FDialog(this).confirm("Are you player?", "Confirm", FDialog.YES_NO_OPTION)) {
			FDialog.NO_OPTION -> master()
			FDialog.YES_OPTION -> player()
		}
		addTimeListener(FTimeListener(3000, 1) { play("res/bad-apple.mp3") })
		super.onInit()
	}

	override fun onExit() = System.exit(0)
}
