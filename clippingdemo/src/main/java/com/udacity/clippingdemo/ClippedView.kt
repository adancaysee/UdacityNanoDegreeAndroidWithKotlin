package com.udacity.clippingdemo

import android.content.Context
import android.graphics.*
import android.os.Build
import android.view.View

/**
 * paper + pen = Canvas
 * Algorithm --> Save current state of canvas
 *               Apply transformations,draw
 *               Restore previous state of canvas
 */


class ClippedView(private val context: Context) : View(context) {

    private val paint = Paint().apply {
        isAntiAlias = true
        strokeWidth = resources.getDimension(R.dimen.strokeWidth)
        textSize = resources.getDimension(R.dimen.textSize)
    }

    private val path = Path()

    private val clipRectRight = resources.getDimension(R.dimen.clipRectRight)
    private val clipRectBottom = resources.getDimension(R.dimen.clipRectBottom)
    private val clipRectTop = resources.getDimension(R.dimen.clipRectTop) //0
    private val clipRectLeft = resources.getDimension(R.dimen.clipRectLeft) //0

    private val rectInset = resources.getDimension(R.dimen.rectInset)
    private val smallRectOffset = resources.getDimension(R.dimen.smallRectOffset)

    private val circleRadius = resources.getDimension(R.dimen.circleRadius)

    private val textOffset = resources.getDimension(R.dimen.textOffset)
    private val textSize = resources.getDimension(R.dimen.textSize)

    //For coordinates x
    private val columnOne = rectInset
    private val columnTwo = columnOne + rectInset + clipRectRight

    //For coordinates y
    private val rowOne = rectInset
    private val rowTwo = rowOne + rectInset + clipRectBottom
    private val rowThree = rowTwo + rectInset + clipRectBottom
    private val rowFour = rowThree + rectInset + clipRectBottom
    private val textRow = rowFour + (1.5f * clipRectBottom)
    private val rejectRow = rowFour + rectInset + 2*clipRectBottom

    private var rectF = RectF(
        rectInset,
        rectInset,
        clipRectRight - rectInset,
        clipRectBottom - rectInset
    )

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        drawBackAndUnClippedRectangle(canvas)
        drawDifferenceClippingExample(canvas)
        drawCircularClippingExample(canvas)
        drawIntersectionClippingExample(canvas)
        drawCombinedClippingExample(canvas)
        drawRoundedRectangleClippingExample(canvas)
        drawOutsideClippingExample(canvas)
        drawSkewedTextExample(canvas)
        drawTranslatedTextExample(canvas)
        drawQuickRejectExample(canvas)
    }

    private fun drawClippedRectangle(canvas: Canvas) {
        //Draw rectangle -- boundaries of my canvas.
        canvas.clipRect(
            clipRectLeft,
            clipRectTop,
            clipRectRight,
            clipRectBottom,
        )
        canvas.drawColor(Color.WHITE)

        //Draw line
        paint.color = Color.RED
        canvas.drawLine(
            clipRectLeft,
            clipRectTop,
            clipRectRight,
            clipRectBottom,
            paint
        )

        //Draw circle
        paint.color = Color.GREEN
        canvas.drawCircle(
            circleRadius,
            (clipRectBottom - circleRadius),
            circleRadius,
            paint
        )

        //Draw text
        paint.color = Color.BLUE
        paint.textSize = textSize
        paint.textAlign = Paint.Align.RIGHT
        canvas.drawText(
            context.getString(R.string.clipping),
            clipRectRight,
            textOffset,
            paint
        )

    }

    private fun drawBackAndUnClippedRectangle(canvas: Canvas) {
        //draw background
        canvas.drawColor(Color.GRAY)
        canvas.save()
        //move canvas
        canvas.translate(columnOne, rowOne)
        //draw un clipped rectangle
        drawClippedRectangle(canvas)
        canvas.restore()
    }

    private fun drawDifferenceClippingExample(canvas: Canvas) {
        canvas.save()
        canvas.translate(columnTwo, rowOne)
        canvas.clipRect(
            2 * rectInset,
            2 * rectInset,
            clipRectRight - 2 * rectInset,
            clipRectBottom - 2 * rectInset
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutRect(
                4 * rectInset,
                4 * rectInset,
                clipRectRight - 4 * rectInset,
                clipRectBottom - 4 * rectInset
            )
        } else {
            @Suppress("DEPRECATION")
            canvas.clipRect(
                4 * rectInset,
                4 * rectInset,
                clipRectRight - 4 * rectInset,
                clipRectBottom - 4 * rectInset,
                Region.Op.DIFFERENCE
            )
        }

        drawClippedRectangle(canvas)
        canvas.restore()

    }

    private fun drawCircularClippingExample(canvas: Canvas) {
        canvas.save()
        canvas.translate(columnOne, rowTwo)

        path.rewind()
        path.addCircle(
            circleRadius,
            (clipRectBottom - circleRadius),
            circleRadius,
            Path.Direction.CCW
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutPath(path)
        } else {
            @Suppress("DEPRECATION")
            canvas.clipPath(path, Region.Op.DIFFERENCE)
        }
        drawClippedRectangle(canvas)

        canvas.restore()
    }

    private fun drawIntersectionClippingExample(canvas: Canvas) {
        canvas.save()
        canvas.translate(columnTwo, rowTwo)

        canvas.clipRect(
            clipRectLeft,
            clipRectTop,
            clipRectRight - smallRectOffset,
            clipRectBottom - smallRectOffset
        )

        canvas.clipRect(
            clipRectLeft + smallRectOffset,
            clipRectTop + smallRectOffset,
            clipRectRight,
            clipRectBottom
        )

        drawClippedRectangle(canvas)
        canvas.restore()
    }

    private fun drawCombinedClippingExample(canvas: Canvas) {
        canvas.save()
        canvas.translate(columnOne, rowThree)

        path.rewind()
        path.addCircle(
            clipRectLeft + circleRadius + rectInset,
            clipRectTop + circleRadius + rectInset,
            circleRadius,
            Path.Direction.CCW
        )
        path.addRect(
            clipRectRight / 2 - circleRadius,
            clipRectTop + circleRadius + rectInset,
            clipRectRight / 2 + circleRadius,
            clipRectBottom - rectInset,
            Path.Direction.CCW
        )
        canvas.clipPath(path)

        drawClippedRectangle(canvas)
        canvas.restore()
    }

    private fun drawRoundedRectangleClippingExample(canvas: Canvas) {
        canvas.save()
        canvas.translate(columnTwo, rowThree)

        path.rewind()
        path.addRoundRect(
            rectF,
            clipRectRight / 4,
            clipRectRight / 4,
            Path.Direction.CCW
        )
        canvas.clipPath(path)

        drawClippedRectangle(canvas)
        canvas.restore()
    }

    private fun drawOutsideClippingExample(canvas: Canvas) {
        canvas.save()
        canvas.translate(columnOne, rowFour)
        canvas.clipRect(
            2 * rectInset,
            2 * rectInset,
            clipRectRight - 2 * rectInset,
            clipRectBottom - 2 * rectInset
        )
        drawClippedRectangle(canvas)
        canvas.restore()
    }

    private fun drawTranslatedTextExample(canvas: Canvas) {
        canvas.save()
        canvas.translate(columnTwo, textRow)
        paint.textAlign = Paint.Align.LEFT
        paint.color = Color.GREEN
        canvas.drawText(
            context.getString(
                R.string.translated
            ),
            clipRectLeft,
            clipRectTop,
            paint,
        )
        canvas.restore()

    }

    private fun drawSkewedTextExample(canvas: Canvas) {
        canvas.save()
        canvas.translate(columnTwo,textRow)
        paint.color = Color.YELLOW
        paint.textAlign = Paint.Align.RIGHT
        canvas.skew(0.2f,0.3f)
        canvas.drawText(
            context.getString(R.string.skewed),
            clipRectLeft,
            clipRectTop,
            paint
        )
        canvas.restore()
    }

    /**
     * With quickReject(), I can decide efficiently which objects you do not have to draw at all,
     * and there is no need to write my own intersection logic.
     * If view is outside return true
     */
    private fun drawQuickRejectExample(canvas: Canvas) {
        val inClipRectangle = RectF(clipRectRight / 2,
            clipRectBottom / 2,
            clipRectRight * 2,
            clipRectBottom * 2)

        /*val notInClipRectangle = RectF(RectF(clipRectRight+1,
            clipRectBottom+1,
            clipRectRight * 2,
            clipRectBottom * 2))*/

        canvas.save()
        canvas.translate(columnOne, rejectRow)
        canvas.clipRect(
            clipRectLeft,clipRectTop,
            clipRectRight,clipRectBottom
        )
        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                canvas.quickReject(inClipRectangle)
            } else {
                @Suppress("DEPRECATION")
                canvas.quickReject(inClipRectangle, Canvas.EdgeType.AA)
            }
        ) {
            canvas.drawColor(Color.WHITE)
        }
        else {
            canvas.drawColor(Color.BLACK)
            canvas.drawRect(inClipRectangle, paint)
        }
        canvas.restore()
    }

}