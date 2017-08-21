package org.ice1000.mgg

import org.frice.game.anim.move.AccelerateMove
import org.frice.game.anim.move.SimpleMove
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
	addObject(1, ShapeObject(res, FRectangle(700, 10), -10.0, 450.0), ShapeObject(res, FRectangle(700, 10), -10.0, 520.0), wall)
	addTimeListener(*it.map {
		FTimeListener("${it.first}".toInt() + 2000, 1, {
			addObject(2, ImageObject(IMAGES["${it.second}"[0]]!!, x = pos(it.second) * 70.0, y = -20.0).apply {
				addAnim(AccelerateMove(0.0, 2.0))
				addAnim(SimpleMove(0, 600))
				addCollider(wall, { if (this.res !in resultImages) this.res = resultImages.last() })
			})
		})
	}.toTypedArray())
	addKeyListener(pressed = {
		if (it.keyCode in KEYS) {
			println("${KEYS[it.keyCode]}, ${layers[2].objects.size}")
			layers[2].objects.forEach {
				if ((it as ImageObject).res !in resultImages) when (it.y) {
					in 430..500 -> it.res = resultImages[0].apply { println("${it.y}") }
					in 410..530 -> it.res = resultImages[1].apply { println("${it.y}") }
					in 380..560 -> it.res = resultImages[2].apply { println("${it.y}") }
				}
			}
		}
	})
}
