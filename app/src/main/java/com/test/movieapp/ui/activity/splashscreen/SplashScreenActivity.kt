package com.test.movieapp.ui.activity.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.test.movieapp.databinding.ActivitySplashScreenBinding
import com.test.movieapp.ui.activity.genre.GenreActivity

class SplashScreenActivity : AppCompatActivity() {

    private val binding: ActivitySplashScreenBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            Handler().postDelayed({
                val intent = Intent(this@SplashScreenActivity, GenreActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
    }
}