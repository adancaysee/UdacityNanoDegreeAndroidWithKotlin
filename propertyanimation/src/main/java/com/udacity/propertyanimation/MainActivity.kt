package com.udacity.propertyanimation

import android.animation.*
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView

/**
 * Property Animation
 * The property is view property(ex: View.ROTATION)
 * AnimatorListenerAdapter is Animator.AnimatorListener. (We cannot use interface directly. Because we need)
 * only start and end listener callback
 */
class MainActivity : AppCompatActivity() {

    private lateinit var starImageview: ImageView
    private lateinit var rotateButton: Button
    private lateinit var translateButton: Button
    private lateinit var scaleButton: Button
    private lateinit var fadeButton: Button
    private lateinit var colorizeButton: Button
    private lateinit var showerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        starImageview = findViewById(R.id.star)
        rotateButton = findViewById(R.id.rotateButton)
        translateButton = findViewById(R.id.translateButton)
        scaleButton = findViewById(R.id.scaleButton)
        fadeButton = findViewById(R.id.fadeButton)
        colorizeButton = findViewById(R.id.colorizeButton)
        showerButton = findViewById(R.id.showerButton)

        rotateButton.setOnClickListener {
            rotater()
        }

        translateButton.setOnClickListener {
            translater()
        }

        scaleButton.setOnClickListener {
            scaler()
        }

        fadeButton.setOnClickListener {
            fader()
        }

        colorizeButton.setOnClickListener {
            colorizer()
        }

        showerButton.setOnClickListener {
            shower()
        }
    }

    private fun rotater() {
        val animator = ObjectAnimator.ofFloat(starImageview, View.ROTATION, -360f, 0f)
        animator.duration = 1000
        animator.disableViewDuringAnimation(rotateButton)
        animator.start()
    }

    private fun translater() {
        val animator = ObjectAnimator.ofFloat(starImageview, View.TRANSLATION_X, 200f)
        //how many times it repeats after the first run
        animator.repeatCount = 1
        //for reversing the direction every time it repeats( first (->) then (<-) )
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(translateButton)
        animator.start()
    }

    /**
     * PropertyValuesHolder -> hold the property and value information for the animation
     * ObjectAnimator -> hold property,value,target
     */
    private fun scaler() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 3f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 3f)

        //All animations run parallel
        val animator = ObjectAnimator.ofPropertyValuesHolder(starImageview, scaleX, scaleY)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(scaleButton)
        animator.start()

    }

    private fun fader() {
        val animator = ObjectAnimator.ofFloat(starImageview, View.ALPHA, 0f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(fadeButton)
        animator.start()
    }

    /**
     * backgroundColor ->  mapped internally to the appropriate setter/getter information on the target object.
     */
    @SuppressLint("ObjectAnimatorBinding")
    private fun colorizer() {
        val animator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ObjectAnimator.ofArgb(starImageview.parent, "backgroundColor", Color.BLACK, Color.RED)
        } else {
            ObjectAnimator.ofInt(starImageview.parent, "backgroundColor", Color.BLACK, Color.RED)
        }
        animator.duration = 500
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(colorizeButton)
        animator.start()
    }

    private fun shower() {
        val container = starImageview.parent as FrameLayout
        val containerW = container.width
        val containerH = container.height
        var starImageW = starImageview.width.toFloat()
        var startImageH = starImageview.height.toFloat()

        val newStar = AppCompatImageView(this)
        newStar.setImageResource(R.drawable.ic_star)
        newStar.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT
        )

        //Change random star size
        newStar.scaleX = Math.random().toFloat() * 1.5f + .1f
        newStar.scaleY = newStar.scaleX

        starImageW *= newStar.scaleX
        startImageH *= newStar.scaleY

        //Change random star position
        newStar.translationX = Math.random().toFloat() * containerW - starImageW / 2

        container.addView(newStar)

        //Create animations
        val moverAnimator =
            ObjectAnimator.ofFloat(
                newStar,
                View.TRANSLATION_Y,
                -startImageH,
                containerH + startImageH
            )
        //Create gentle acceleration motion
        moverAnimator.interpolator = AccelerateInterpolator(1f)

        val rotateAnimator =
            ObjectAnimator.ofFloat(newStar, View.ROTATION, (Math.random() * 1080).toFloat())

        //Constant rate
        rotateAnimator.interpolator = LinearInterpolator()

        val set = AnimatorSet()
        set.playTogether(moverAnimator, rotateAnimator)
        set.duration = (Math.random() * 1500 + 500).toLong()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                container.removeView(newStar)
            }
        })
        set.start()
    }

    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                view.isEnabled = true
            }
        })
    }

}