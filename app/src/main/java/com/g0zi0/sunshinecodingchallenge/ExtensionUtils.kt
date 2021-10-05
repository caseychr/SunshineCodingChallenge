package com.g0zi0.sunshinecodingchallenge

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView

fun String.capitalizeFirstLetters(): String { //TODO belongs in extension functions class
    val words = this.split(" ").toMutableList()
    var response = ""
    for (word in words) {
        response += word.capitalize()+" "
    }
    response = response.trim()
    return response
}

fun View.fadeInText() {
    var view = this
    val animatorSet = AnimatorSet()
    animatorSet.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator?) {

        }

        override fun onAnimationEnd(p0: Animator?) {
            view.visibility = View.VISIBLE
        }
        override fun onAnimationCancel(p0: Animator?) {

        }
        override fun onAnimationRepeat(p0: Animator?) {

        }

    })
    val textAnimator = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f)
    textAnimator.duration = 1500L
    //textAnimator.start()
    animatorSet.play(textAnimator)
    animatorSet.start()
}

fun ImageView.rotateButton() {
    val imageAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f)
    imageAnimator.duration = 1000L
    imageAnimator.start()
}