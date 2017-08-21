package org.ice1000.mgg

import org.frice.game.Game
import org.frice.game.obj.sub.ImageObject
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.audio.play
import org.frice.game.utils.message.FDialog
import org.frice.game.utils.time.FTimeListener
import org.frice.game.utils.time.FTimer

class MusicGame : Game(3) {
	override fun onExit() = System.exit(0)
	override fun onInit() {
		setSize(600, 600)
		title = "ice1000's music game"
		autoGC = true
		addObject(0, ImageObject(ImageResource.fromPath("res/img/BG.jpg")))
		addTimeListener(FTimeListener(3000, 1) { play("res/song.mp3") })
		super.onInit()
	}

	val seconds = FTimer(1000)
	var count = 2
	override fun onLastInit() {
		when (FDialog(this).confirm("Are you player?", "Confirm", FDialog.YES_NO_OPTION)) {
			FDialog.NO_OPTION -> master()
			FDialog.YES_OPTION -> player()
			else -> System.exit(0)
		}
		super.onLastInit()
	}

	override fun onRefresh() {
		super.onRefresh()
		if (seconds.ended() && count >= 0) println("${count--}")
	}
}
