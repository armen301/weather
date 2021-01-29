package com.e.weatherapp.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.e.weatherapp.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            delay(3000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }
    }
}