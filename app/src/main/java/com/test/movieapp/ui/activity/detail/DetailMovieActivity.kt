package com.test.movieapp.ui.activity.detail

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.test.movieapp.databinding.ActivityDetailMovieBinding


class DetailMovieActivity : AppCompatActivity() {

    private val binding: ActivityDetailMovieBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            tvDetailTitle.text = intent.getStringExtra("title")
            tvDescriptionMovie.text = intent.getStringExtra("original_title")
            tvDescriptionDetail.text = intent.getStringExtra("overview")
            Glide.with(this@DetailMovieActivity)
                .load(intent.getStringExtra("image"))
                .skipMemoryCache(true)
                .centerCrop()
                .into(imgDetailMovie)

            supportActionBar?.title = intent.getStringExtra("title")
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}