package org.ice1000.mgg

import org.frice.game.Game.Initializer.launch
import org.frice.game.resource.image.FileImageResource
import java.awt.event.KeyEvent

fun main(args: Array<String>) = launch(MusicGame::class.java)
val IMAGES = mapOf(*"ASDFJKL;".map { it to FileImageResource("res/img/VK_$it.png") }.toTypedArray())
fun pos(any: Any) = "ASDFJKL;".indexOf("$any"[0])
val KEYS = mapOf(KeyEvent.VK_A to "A", KeyEvent.VK_S to "S", KeyEvent.VK_D to "D", KeyEvent.VK_F to "F",
		KeyEvent.VK_J to "J", KeyEvent.VK_K to "K", KeyEvent.VK_L to "L", KeyEvent.VK_SEMICOLON to ";")
