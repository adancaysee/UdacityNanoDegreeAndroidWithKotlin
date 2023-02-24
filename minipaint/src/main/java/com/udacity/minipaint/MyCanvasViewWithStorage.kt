package com.udacity.minipaint

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import kotlin.math.abs


class MyCanvasViewWithStorage(context: Context) : View(context) {

    // Path representing the drawing so far
    private val drawing = Path()

    // Path representing what's currently being drawn
    private val curPath = Path()

    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)
    private val drawColor = ResourcesCompat.getColor(resources, R.color.colorPaint, null)

    private val paint = Paint().apply {
        color = drawColor
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = STROKE_WIDTH
    }

    //For caching the x and y coordinates of the current touch event (the MotionEvent coordinates)
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    /*
    For caching the latest x and y values
    After the user stops moving and lifts their touch, these are the starting point for the next path
     */
    private var currentX = 0f
    private var currentY = 0f

    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    private val path = Path()

    private lateinit var frame: Rect

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val inset = 40
        frame = Rect(inset,inset,width-inset,height-inset)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        canvas.drawColor(backgroundColor)

        canvas.drawPath(drawing,paint)

        canvas.drawPath(curPath,paint)

        //paint.color = drawColor
        canvas.drawRect(frame,paint)


    }

    override fun performClick(): Boolean {
        if (super.performClick()) return true
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        performClick()
        event?.let {
            motionTouchEventX = it.x
            motionTouchEventY = it.y
            when (it.action) {
                MotionEvent.ACTION_DOWN -> touchStart()
                MotionEvent.ACTION_MOVE -> touchMove()
                MotionEvent.ACTION_UP -> touchUp()
            }
        }
        return true
    }

    private fun touchStart() {
        path.reset()
        //Set the beginning
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchMove() {
        val dx = abs(motionTouchEventX - currentX)
        val dy = abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            // QuadTo() adds a quadratic bezier from the last point,
            // approaching control point (x1,y1), and ending at (x2,y2).
            path.quadTo(
                currentX,
                currentY,
                (motionTouchEventX + currentX) / 2,
                (motionTouchEventY + currentY) / 2
            )
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            // Draw the path in the extra bitmap to cache it.
            curPath.addPath(path)
        }
        invalidate()
    }

    private fun touchUp() {
        path.reset()
        drawing.addPath(curPath)
        curPath.reset()
    }

}