package com.demo.pokemon

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        APPLICATION = this
    }

    companion object {
        var APPLICATION: App? = null
    }
}