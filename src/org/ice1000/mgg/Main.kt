package org.ice1000.mgg

import org.frice.game.Game
import org.frice.game.Game.Initializer.launch
import org.frice.game.anim.move.SimpleMove
import org.frice.game.obj.sub.ImageObject
import org.frice.game.resource.image.FileImageResource
import org.frice.game.utils.audio.play
import org.frice.game.utils.data.Preference
import org.frice.game.utils.data.string2File
import org.frice.game.utils.message.FDialog
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
		KeyEvent.VK_SEMICOLON to ";")

val IMAGES = mapOf(*"ASDFJKL;".map { it to FileImageResource("res/img/VK_$it.png") }.toTypedArray())

class MusicGame : Game(3) {
	override fun onInit() {
		setSize(600, 600)
		title = "ice1000's music game"
		"".string2File("save.db")
		when (FDialog(this).confirm("Practise mode?", "Confirm", FDialog.YES_NO_OPTION)) {
			FDialog.NO_OPTION -> Preference("save.db").let { database ->
				addKeyListener(pressed = { if (it.keyCode in KEYS) database.insert("${Clock.current}", "${KEYS[it.keyCode]}") })
			}
			FDialog.YES_OPTION -> Preference("data.db").list().let {
				addTimeListener(*it.map {
					FTimeListener("${it.first}".toInt(), 1, {
						addObject(2, ImageObject(IMAGES["${it.second}"[0]]!!, x = "ASDFJKL;".indexOf("${it.second}"[0]) * 70.0, y = -20.0).apply {
							addAnim(SimpleMove(0, 300))
						})
					})
				}.toTypedArray())
			}
		}
		addTimeListener(FTimeListener(2000, 1) {
			play("res/bad-apple.mp3")
		})
		super.onInit()
	}

	override fun onExit() {
		// super.onExit()
		System.exit(0)
	}
}

fun main(args: Array<String>) = launch(MusicGame::class.java)
