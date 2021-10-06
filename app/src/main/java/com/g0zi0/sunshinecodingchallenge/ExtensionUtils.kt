package com.g0zi0.sunshinecodingchallenge

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
    this.apply {
        alpha = 0f
        visibility = View.VISIBLE
        animate().alpha(1f).duration = 2000L
    }
}

fun ImageView.rotateButton() {
    val imageAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f)
    imageAnimator.duration = 1000L
    imageAnimator.start()
}