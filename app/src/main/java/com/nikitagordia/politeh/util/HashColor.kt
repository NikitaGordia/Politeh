package com.nikitagordia.politeh.util

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.shapes.RoundRectShape

object HashColor{

    val KEY_RED = 31L
    val KEY_GREEN = 83L
    val KEY_BLUE = 51L
    val MOD : Long = 1000_000_000 + 7

    fun hash(s : String, type : Char) : Int {
        var res = 0L
        var power = 1L
        for (i in s) {
            when(type) {
                'r' -> power = (power * KEY_RED) % MOD
                'g' -> power = (power * KEY_GREEN) % MOD
                else -> power = (power * KEY_BLUE) % MOD
            }
            res = (((i.toInt() * power) % MOD + res) % MOD)
        }
        return res.toInt() % 200
    }

    fun getColor(s : String) = Color.rgb(
            hash(s, 'r'),
            hash(s, 'g'),
            hash(s, 'b')
    )

    fun getShapeDrawable(s : String) : ShapeDrawable {
        val result = ShapeDrawable()
        return result.apply {
            val radii = FloatArray(8, { 11F })
            shape = RoundRectShape(radii, null, null)
            paint.color = Color.rgb(
                    hash(s, 'r'),
                    hash(s, 'g'),
                    hash(s, 'b')
            )
        }
    }


}