package com.test.movieapp.ui.activity.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.test.movieapp.R
import com.test.movieapp.databinding.ActivityDetailMovieBinding
import com.test.movieapp.util.VmData


class DetailMovieActivity : AppCompatActivity() {

    private val binding: ActivityDetailMovieBinding by viewBinding()
    private val viewModel: DetailMovieViewModel by viewModels()
    private val adapter: DetailMovieAdapter by lazy {
        DetailMovieAdapter()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setSupportActionBar(toolbarMovie)
            var isShow = true
            var scrollRange = -1
            binding.appBarMovie.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
                if (scrollRange == -1) {
                    scrollRange = barLayout?.totalScrollRange!!
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = intent.getStringExtra("title")
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    toolbarMovie.setNavigationIcon(R.drawable.ic_arrow_24dp)
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = " "
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    isShow = false
                }
            })

            btnBackDetail.setOnClickListener {
                finish()
            }

            tvDetail.text = intent.getStringExtra("title")
            tvDescription.text = intent.getStringExtra("overview")
            tvTanggal.text = "Rilis : " + intent.getStringExtra("rilis")
            tvRate.text = "Vote Average : " + intent.getStringExtra("vote")
            Glide.with(this@DetailMovieActivity)
                .load(intent.getStringExtra("poster"))
                .skipMemoryCache(true)
                .centerCrop()
                .into(imgMovie)

            val idMovie = intent.getIntExtra("id", 0)
            viewModel.initPaging(this@DetailMovieActivity, idMovie)
        }

        setObservableReview()
    }

    private fun setObservableReview() {
        viewModel.review.observe(this) {
            when (it) {
                is VmData.Loading -> {

                }

                is VmData.Empty -> {

                }

                is VmData.Success -> {

                }
                is VmData.Failure -> {

                }
            }
            viewModel.data.observe(this, adapter::submitList)
            binding.rvReview.adapter = adapter
            binding.rvReview.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}