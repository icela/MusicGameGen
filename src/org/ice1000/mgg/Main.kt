package org.ice1000.mgg

import org.frice.game.Game
import org.frice.game.Game.Companion.launch
import org.frice.game.resource.image.FileImageResource
import org.frice.game.utils.audio.play
import org.frice.game.utils.data.Preference
import org.frice.game.utils.data.string2File
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

val IMAGES = "ASDFJKL;".map { FileImageResource("res/img/VK_$it.png") }

class MusicGame : Game(3) {
	override fun onInit() {
		setSize(600, 600)
		title = "ice1000's music game"
		"".string2File("save.db")
		Preference("save.db").let { database ->
			addKeyListener(pressed = {
				if (it.keyCode in KEYS) database.insert("${Clock.current}", "${KEYS[it.keyCode]}")
			})
		}
		addTimeListener(FTimeListener(2000, 2) {
			play("res/bad-apple.mp3")
		})
		super.onInit()
	}

	override fun onLastInit() {
		super.onLastInit()
	}

	override fun onExit() {
		// super.onExit()
		System.exit(0)
	}
}

fun main(args: Array<String>) = launch(MusicGame::class.java)
