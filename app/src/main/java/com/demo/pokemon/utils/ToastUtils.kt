package com.demo.pokemon.utils

import android.widget.Toast
import androidx.annotation.StringRes
import com.demo.pokemon.App

object ToastUtils {
    private val appContext by lazy {
        App.APPLICATION
    }
    private const val LONG = Toast.LENGTH_LONG
    private const val SHORT = Toast.LENGTH_SHORT
    private var toast: Toast ? = null
    private val jobWasCancelledThrowable = Throwable("Job was cancelled")

    fun showLongToast(text: String?) {
        showToast(text, LONG)
    }

    fun showLongToast(@StringRes stringResId: Int) {
        var text = appContext?.resources?.getString(stringResId)
        text?.let {
            showToast(text, LONG)
        }
    }

    fun showShortToast(text: String?) {
        showToast(text, SHORT)
    }

    fun showShortToast(@StringRes stringResId: Int) {
        var text = appContext?.resources?.getString(stringResId)
        showToast(text, SHORT)
    }

    fun showToast(@StringRes stringResId: Int, duration: Int) {
        var text = appContext?.resources?.getString(stringResId)
        showToast(text, duration)
    }

    fun showToast(text: String?, duration: Int) {
        if (text == jobWasCancelledThrowable.message) return
        text?.let {
            if (toast == null) {
                toast = Toast.makeText(appContext, text, duration)
            } else {
                toast?.duration = duration
            }
            toast?.show()
        }
    }
}
