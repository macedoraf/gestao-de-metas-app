package br.com.fiap

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("isVisible")
fun View.isVisible(boolean: Boolean?) {
    val visibility = when (boolean) {
        true -> {
            View.VISIBLE
        }
        false -> {
            View.GONE
        }
        else -> {
            View.GONE
        }
    }

    setVisibility(visibility)
}