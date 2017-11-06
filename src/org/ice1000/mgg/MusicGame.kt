package org.ice1000.mgg

import org.frice.Game
import org.frice.anim.move.SimpleMove
import org.frice.obj.button.SimpleText
import org.frice.obj.sub.ImageObject
import org.frice.obj.sub.ShapeObject
import org.frice.resource.graphics.ColorResource
import org.frice.resource.image.ImageResource
import org.frice.utils.audio.play
import org.frice.utils.data.Preference
import org.frice.utils.message.FDialog
import org.frice.utils.shape.FRectangle
import org.frice.utils.time.*

class MusicGame : Game(3) {
	override fun onExit() = System.exit(0)
	override fun onInit() {
		setSize(600, 600)
		title = "ice1000's music game"
		autoGC = true
		addObject(0, ImageObject(ImageResource.fromPath("res/img/BG.jpg")))
		addTimeListener(FTimeListener(3000, 1) { play("res/song.mp3") })
	}

	private val seconds = FTimer(1000)
	private var count = 2
	override fun onLastInit() {
		when (FDialog(this).confirm("Are you player?", "Confirm", FDialog.YES_NO_OPTION)) {
			FDialog.NO_OPTION -> Preference("data.db").let { db ->
				addKeyListener(pressed = { if (it.keyCode in KEYS) db.insert("${FClock.current - 3000}", "${KEYS[it.keyCode]}") })
			}
			FDialog.YES_OPTION -> Preference("data.db").list().let {
				val res = ColorResource.BLUE
				val resultImages = listOf("Perfect", "Good", "Bad", "Miss").map { ImageResource.fromPath("res/img/$it.png") }
				val wall = ShapeObject(ColorResource.COLORLESS, FRectangle(700, 10), -10.0, 590.0)
				var scoreC = 0
				var comboC = 0
				val show = SimpleText(ColorResource.BLACK, "score", 20.0, 20.0)
				val updateShow = { show.text = "score: $scoreC, combo: $comboC" }
				addObject(1, ShapeObject(res, FRectangle(700, 10), -10.0, 480.0),
						ShapeObject(res, FRectangle(700, 10), -10.0, 540.0), wall, show)
				addTimeListener(*it.map {
					FTimeListener("${it.first}".toInt() + 2600, 1, {
						addObject(2, ImageObject(IMAGES["${it.second}"[0]]!!, x = pos(it.second) * 70.0, y = -20.0).apply {
							addAnim(SimpleMove(0, 500))
							addCollider(wall, {
								if (this.res !in resultImages) {
									comboC = 0
									updateShow()
									this.res = resultImages.last()
								}
							})
						})
					})
				}.toTypedArray())
				addKeyListener(pressed = {
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
			else -> System.exit(0)
		}
	}

	override fun onRefresh() {
		if (seconds.ended() && count >= 0) println("${count--}")
	}
}
