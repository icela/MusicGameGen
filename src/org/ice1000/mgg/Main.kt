package org.ice1000.mgg

import org.frice.game.Game
import org.frice.game.Game.Companion.launch
import org.frice.game.utils.audio.play
import org.frice.game.utils.message.log.FLog
import org.frice.game.utils.time.Clock
import org.frice.game.utils.time.FTimeListener
import java.awt.event.KeyEvent

val KEYS = mapOf(
		KeyEvent.VK_A to "A",
		KeyEvent.VK_S to "S",
		KeyEvent.VK_D to "D",
		KeyEvent.VK_F to "F",
		KeyEvent.VK_J to "J",
		KeyEvent.VK_K to "K",
		KeyEvent.VK_L to "L",
		KeyEvent.VK_SEMICOLON to ";"
)

class MusicGame : Game(3) {
	override fun onInit() {
		setSize(600, 600)
		title = "ice1000's music game"
		addKeyListener(pressed = {
			if (it.keyCode in KEYS) FLog.v("${Clock.current}: ${KEYS[it.keyCode]}")
		})
		addTimeListener(FTimeListener(2000, times = 2, timeUp = {
			play("res/bad-apple.mp3")
		}))
		super.onInit()
	}

	override fun onLastInit() {
		super.onLastInit()
	}
}

fun main(args: Array<String>) = launch(MusicGame::class.java)
