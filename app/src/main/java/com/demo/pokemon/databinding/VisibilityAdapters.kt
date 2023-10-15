package com.demo.pokemon.databinding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleWhen")
fun visibleWhen(view: View, visible: Boolean?) = when (visible) {
    true -> view.visibility = View.VISIBLE
    else -> view.visibility = View.GONE
}