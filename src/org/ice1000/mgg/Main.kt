package org.ice1000.mgg

import org.frice.game.Game
import org.frice.game.Game.Companion.launch
import org.frice.game.utils.audio.play
import java.awt.event.KeyEvent

val KEYS = listOf(
		KeyEvent.VK_A,
		KeyEvent.VK_S,
		KeyEvent.VK_D,
		KeyEvent.VK_F,
		KeyEvent.VK_J,
		KeyEvent.VK_K,
		KeyEvent.VK_L,
		KeyEvent.VK_SEMICOLON
)

class MusicGame : Game(3) {
	override fun onInit() {
		setSize(600, 600)
		title = "ice1000's music game"
		addKeyListener(pressed = {
			if (it.keyCode in KEYS) {

			}
		})
		super.onInit()
	}

	override fun onLastInit() {
		play("res/bad-apple.mp3")
		super.onLastInit()
	}
}

fun main(args: Array<String>) = launch(MusicGame::class.java)
