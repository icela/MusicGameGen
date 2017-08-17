package org.ice1000.mgg

import org.frice.game.Game
import org.frice.game.Game.Initializer.launch
import org.frice.game.resource.image.FileImageResource
import org.frice.game.utils.audio.play
import org.frice.game.utils.data.string2File
import org.frice.game.utils.message.FDialog
import org.frice.game.utils.time.FTimeListener
import java.awt.event.KeyEvent

fun main(args: Array<String>) = launch(MusicGame::class.java)
val IMAGES = mapOf(*"ASDFJKL;".map { it to FileImageResource("res/img/VK_$it.png") }.toTypedArray())
fun pos(any: Any) = "ASDFJKL;".indexOf("$any"[0])
val KEYS = mapOf(KeyEvent.VK_A to "A", KeyEvent.VK_S to "S", KeyEvent.VK_D to "D", KeyEvent.VK_F to "F",
		KeyEvent.VK_J to "J", KeyEvent.VK_K to "K", KeyEvent.VK_L to "L", KeyEvent.VK_SEMICOLON to ";")

class MusicGame : Game(3) {
	override fun onInit() {
		setSize(600, 600)
		title = "ice1000's music game"
		autoGC = true
		"".string2File("save.db")
		when (FDialog(this).confirm("Practise mode?", "Confirm", FDialog.YES_NO_OPTION)) {
			FDialog.NO_OPTION -> player()
			FDialog.YES_OPTION -> master()
		}
		addTimeListener(FTimeListener(3000, 1) { play("res/bad-apple.mp3") })
		super.onInit()
	}

	override fun onExit() = System.exit(0)
}
