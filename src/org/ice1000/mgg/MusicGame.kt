package org.ice1000.mgg

import org.frice.Game
import org.frice.anim.move.SimpleMove
import org.frice.obj.SideEffect
import org.frice.obj.button.SimpleText
import org.frice.obj.sub.ImageObject
import org.frice.obj.sub.ShapeObject
import org.frice.resource.graphics.ColorResource
import org.frice.resource.image.ImageResource
import org.frice.util.data.Preference
import org.frice.util.media.play
import org.frice.util.shape.FRectangle
import org.frice.util.time.FClock
import org.frice.util.time.FTimer
import java.util.function.Consumer

class MusicGame : Game(3) {
	private val seconds = FTimer(1000)
	private lateinit var wall: ShapeObject
	override fun onExit() = System.exit(0)
	private val collisionEvents = arrayListOf<Pair<ImageObject, Runnable>>()
	private fun ImageObject.addCollider(runnable: Runnable) = collisionEvents.add(to(runnable))

	override fun onInit() {
		setSize(600, 600)
		title = "ice1000's music game"
		autoGC = true
		addObject(0, ImageObject(ImageResource.fromPath("res/img/BG.jpg")))
		runLater(3000, SideEffect { play("res/song.mp3") })
	}

	private var count = 2
	override fun onLastInit() = if (dialogConfirmYesNo("Are you player?", "Confirm")) Preference("data.db").let { db ->
		addKeyListener(pressed = Consumer { if (it.keyCode in KEYS) db.insert("${FClock.current - 3000}", "${KEYS[it.keyCode]}") })
	}
	else Preference("data.db").list().let {
		val res = ColorResource.BLUE
		val resultImages = listOf("Perfect", "Good", "Bad", "Miss").map { ImageResource.fromPath("res/img/$it.png") }
		wall = ShapeObject(ColorResource.COLORLESS, FRectangle(700, 10), -10.0, 590.0)
		var (scoreC, comboC) = 0 to 0
		val show = SimpleText(ColorResource.BLACK, "score", 20.0, 20.0)
		val updateShow = { show.text = "score: $scoreC, combo: $comboC" }
		addObject(1, ShapeObject(res, FRectangle(700, 10), -10.0, 480.0),
				ShapeObject(res, FRectangle(700, 10), -10.0, 540.0), wall, show)
		it.forEach { (time, obj) ->
			runLater(time.toLong() + 2600, SideEffect {
				addObject(2, ImageObject(IMAGES["$obj"[0]]!!, x = pos(obj) * 70.0, y = -20.0).apply {
					addAnim(SimpleMove(0, 500))
					addCollider(Runnable {
						if (this.res !in resultImages) {
							comboC = 0
							updateShow()
							this.res = resultImages.last()
						}
					})
				})
			})
		}
		addKeyListener(pressed = Consumer {
			if (it.keyCode in KEYS) {
				println("${KEYS[it.keyCode]}, ${layers[2].objects.size}")
				layers[2].objects.forEach { obj ->
					if ((obj as ImageObject).res !in resultImages && IMAGES[KEYS[it.keyCode]!![0]] == obj.res) {
						try {
							val (r, c) = when (obj.y) {
								in 400..500 -> Pair(resultImages[0], 10)
								in 380..530 -> Pair(resultImages[1], 5)
								in 360..560 -> Pair(resultImages[2], 1)
								else -> throw RuntimeException()
							}
							obj.res = r
							scoreC += c
						} catch (e: RuntimeException) {
							println("${obj.y}")
							comboC++
						}
						updateShow()
					}
				}
			}
		})
	}

	override fun onRefresh() {
		if (seconds.ended() && count >= 0) println("${count--}")
		collisionEvents.removeIf { (obj, event) ->
			if (obj.died) return@removeIf true
			if (obj.collides(wall)) event.run()
			false
		}
	}
}
