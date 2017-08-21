package org.ice1000.mgg

import org.frice.game.anim.move.SimpleMove
import org.frice.game.obj.button.SimpleText
import org.frice.game.obj.sub.ImageObject
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.data.Preference
import org.frice.game.utils.graphics.shape.FRectangle
import org.frice.game.utils.time.FTimeListener

fun MusicGame.player() = Preference("data.db").list().let {
	val res = ColorResource.BLUE
	val resultImages = listOf("Perfect", "Good", "Bad", "Miss").map { ImageResource.fromPath("res/img/$it.png") }
	val wall = ShapeObject(ColorResource.COLORLESS, FRectangle(700, 10), -10.0, 590.0)
	var scoreC = 0
	val score = SimpleText(ColorResource.BLACK, "score", 20.0, 20.0)
	addObject(1, ShapeObject(res, FRectangle(700, 10), -10.0, 450.0), ShapeObject(res, FRectangle(700, 10), -10.0, 540.0), wall, score)
	addTimeListener(*it.map {
		FTimeListener("${it.first}".toInt() + 2600, 1, {
			addObject(2, ImageObject(IMAGES["${it.second}"[0]]!!, x = pos(it.second) * 70.0, y = -20.0).apply {
				addAnim(SimpleMove(0, 500))
				addCollider(wall, { if (this.res !in resultImages) this.res = resultImages.last() })
			})
		})
	}.toTypedArray())
	addKeyListener(pressed = {
		if (it.keyCode in KEYS) {
			println("${KEYS[it.keyCode]}, ${layers[2].objects.size}")
			layers[2].objects.forEach { obj ->
				var printRes = true
				if ((obj as ImageObject).res !in resultImages && IMAGES[KEYS[it.keyCode]!![0]] == obj.res) {
					when (obj.y) {
						in 400..500 -> {
							obj.res = resultImages[0]
							scoreC += 100
						}
						in 380..530 -> {
							obj.res = resultImages[1]
							scoreC += 50
						}
						in 360..560 -> {
							obj.res = resultImages[2]
							scoreC += 10
						}
						else -> printRes = false
					}
					if (printRes) println("${obj.y}")
					score.text = "score: $scoreC"
				}
			}
		}
	})
}
