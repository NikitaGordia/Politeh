package com.nikitagordia.politeh.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView

import com.nikitagordia.politeh.R

/**
 * Created by nikitagordia on 5/25/18.
 */

class CircleNumberView : FrameLayout {

    private lateinit var circle: View
    private lateinit var number: TextView

    private constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_circle_number_view, this, true)
        circle = view.findViewById(R.id.circle)
        number = view.findViewById<TextView>(R.id.number)
    }

    fun setup(num: Int, shape: ShapeDrawable) {
        number.text = num.toString()
        circle.background = shape
    }

    fun setup(day: String) {
        number.text = day
    }
}
