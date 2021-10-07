package com.g0zi0.sunshinecodingchallenge

import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView

/**
 * Capitalizes the first letter of each word in the string
 */
fun String.capitalizeFirstLetters(): String {
    val words = this.split(" ").toMutableList()
    var response = ""
    for (word in words) {
        response += word.capitalize()+" "
    }
    response = response.trim()
    return response
}

/**
 * fades in textviews for current weather
 */
fun View.fadeInText() {
    this.apply {
        alpha = 0f
        visibility = View.VISIBLE
        animate().alpha(1f).duration = 2000L
    }
}

/**
 * rotates the refresh button to show it was clicked
 */
fun ImageView.rotateButton() {
    val imageAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f)
    imageAnimator.duration = 1000L
    imageAnimator.start()
}