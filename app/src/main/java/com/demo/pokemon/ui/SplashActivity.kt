package com.demo.pokemon.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isTaskRoot && shouldFinish()) {
            finish()
            return
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val sendIntent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(sendIntent)
            finish()
        }, 2000)
    }

    private fun shouldFinish() = intent.let {
        it.hasCategory(Intent.CATEGORY_LAUNCHER) &&
                it.action != null &&
                it.action == Intent.ACTION_MAIN
    }
}
