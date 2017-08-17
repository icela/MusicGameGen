package org.ice1000.mgg

import org.frice.game.anim.move.SimpleMove
import org.frice.game.obj.sub.ImageObject
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.utils.data.Preference
import org.frice.game.utils.graphics.shape.FRectangle
import org.frice.game.utils.time.FTimeListener

fun MusicGame.master() {
	Preference("data.db").list().let {
		addObject(ShapeObject(ColorResource.BLUE, FRectangle(700, 10), -10.0, 500.0),
				ShapeObject(ColorResource.BLUE, FRectangle(700, 10), -10.0, 570.0))
		addTimeListener(*it.map {
			FTimeListener("${it.first}".toInt() - 1500, 1, {
				addObject(2, ImageObject(IMAGES["${it.second}"[0]]!!, x = pos(it.second) * 70.0, y = -20.0).apply {
					// addAnim(AccelerateMove(0.0, 2.0))
					addAnim(SimpleMove(0, 300))
				})
			})
		}.toTypedArray())
		addKeyListener(pressed = {
			if (it.keyCode in KEYS) {
				println(KEYS[it.keyCode])
			}
		})
	}

}