package com.udacity.customfancontroller

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

private enum class FanSpeed(val label: Int) {
    OFF(R.string.fan_off),
    LOW(R.string.fan_low),
    MEDIUM(R.string.fan_medium),
    HIGH(R.string.fan_high);

    fun next(): FanSpeed = when (this) {
        OFF -> LOW
        LOW -> MEDIUM
        MEDIUM -> HIGH
        HIGH -> OFF
    }
}

private const val RADIUS_OFFSET_LABEL = 30
private const val RADIUS_OFFSET_INDICATOR = -35


class DialView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    private var radius = 0.0f
    private var fanSpeed = FanSpeed.OFF

    private var fanSpeedLowColor = 0
    private var fanSpeedMediumColor = 0
    private var fanSeedMaxColor = 0

    private val pointPosition: PointF = PointF(0.0f, 0.0f)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    init {
        isClickable = true

        context.withStyledAttributes(attrs,R.styleable.DialView) {
            fanSpeedLowColor = getColor(R.styleable.DialView_fanColor1,0)
            fanSpeedMediumColor = getColor(R.styleable.DialView_fanColor2,0)
            fanSeedMaxColor = getColor(R.styleable.DialView_fanColor3,0)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas == null) {
            return
        }

        //Draw fan
        paint.color = when (fanSpeed) {
            FanSpeed.OFF -> Color.GRAY
            FanSpeed.LOW -> fanSpeedLowColor
            FanSpeed.MEDIUM -> fanSpeedMediumColor
            FanSpeed.HIGH -> fanSeedMaxColor
        }
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)

        //draw indicator
        val markerRadius = radius + RADIUS_OFFSET_INDICATOR
        pointPosition.computeXYForSpeed(fanSpeed, markerRadius)
        paint.color = Color.BLACK
        canvas.drawCircle(pointPosition.x, pointPosition.y, radius / 12, paint)

        //draw label
        val labelRadius = radius + RADIUS_OFFSET_LABEL
        for (fanSpeed in FanSpeed.values()) {
            val label = resources.getString(fanSpeed.label)
            pointPosition.computeXYForSpeed(fanSpeed, labelRadius)
            canvas.drawText(label, pointPosition.x, pointPosition.y, paint)
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        /**
         * We did this calculation here instead of onDraw(We do that with with,height attributes of views)
         * But we didn't do. Because we want that onDraw is as fast as possible
         */
        radius = (min(w, h) / 2 * 0.8).toFloat()
    }

    override fun performClick(): Boolean {
        if (super.performClick()) return true

        fanSpeed = fanSpeed.next()
        contentDescription = resources.getString(fanSpeed.label)

        invalidate()
        return true

    }


    private fun PointF.computeXYForSpeed(pos: FanSpeed, radius: Float) {
        // Angles are in radians.
        val startAngle = Math.PI * (9 / 8.0)
        val angle = startAngle + pos.ordinal * (Math.PI / 4)
        x = (radius * cos(angle)).toFloat() + width / 2
        y = (radius * sin(angle)).toFloat() + height / 2
    }


}