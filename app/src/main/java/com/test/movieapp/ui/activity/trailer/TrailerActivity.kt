package com.test.movieapp.ui.activity.trailer

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.onClick
import com.oratakashi.viewbinding.core.tools.visible
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.test.movieapp.databinding.ActivityTrailerBinding

class TrailerActivity : AppCompatActivity() {
    private val binding: ActivityTrailerBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {

            val key = intent.getStringExtra("id")
            tvTitle.text = intent.getStringExtra("name")

            hideSystemUI()
            showSystemUI()

            ytPlayer.also {
                lifecycle.addObserver(it)
                it.initialize(
                    object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            super.onReady(youTubePlayer)
                            youTubePlayer.cueVideo(key!!, 0F)
                            youTubePlayer.play()
                        }
                    }, true
                )
                it.addFullScreenListener(object : YouTubePlayerFullScreenListener {
                    override fun onYouTubePlayerEnterFullScreen() {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        ivBack.gone()
                        this@TrailerActivity.hideSystemUI()
                    }

                    override fun onYouTubePlayerExitFullScreen() {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        ivBack.visible()
                        this@TrailerActivity.showSystemUI()
                    }
                })
                it.enableBackgroundPlayback(true)

            }

            tvYouTube.onClick {
                val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${key}"))
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=${key}")
                )
                try {
                    startActivity(appIntent)
                } catch (ex: ActivityNotFoundException) {
                    startActivity(webIntent)
                }
            }
        }
    }

    private fun hideSystemUI() {
        val decorView: View = window.decorView
        val uiOptions: Int = decorView.systemUiVisibility
        var newUiOptions = uiOptions
        newUiOptions = newUiOptions or View.SYSTEM_UI_FLAG_LOW_PROFILE
        newUiOptions = newUiOptions or View.SYSTEM_UI_FLAG_FULLSCREEN
        newUiOptions = newUiOptions or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        newUiOptions = newUiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE
        newUiOptions = newUiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        decorView.systemUiVisibility = newUiOptions
    }

    private fun showSystemUI() {
        val decorView: View = window.decorView
        val uiOptions: Int = decorView.systemUiVisibility
        var newUiOptions = uiOptions
        newUiOptions = newUiOptions and View.SYSTEM_UI_FLAG_LOW_PROFILE.inv()
        newUiOptions = newUiOptions and View.SYSTEM_UI_FLAG_FULLSCREEN.inv()
        newUiOptions = newUiOptions and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION.inv()
        newUiOptions = newUiOptions and View.SYSTEM_UI_FLAG_IMMERSIVE.inv()
        newUiOptions = newUiOptions and View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY.inv()
        decorView.systemUiVisibility = newUiOptions
    }
}