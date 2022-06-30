package com.test.movieapp.ui.activity.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.test.movieapp.data.model.movie.ResultsItem
import com.test.movieapp.databinding.ActivityMainBinding
import com.test.movieapp.ui.activity.detail.DetailMovieActivity
import com.test.movieapp.util.VmData

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: MovieViewModel by viewModels()
    private val adapter: MovieAdapter by lazy {
        MovieAdapter {
            getDetail(it)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name: String = intent.getStringExtra("name").toString()
        val id: Int = intent.getIntExtra("id", 0)

        with(binding) {

            tvGenre.text = name
            tvDesc.text = "Ayo Koleksi Film dengan Genre ${name}"

            viewModel.initPaging(
                this@MainActivity, id
            )
            swipeRefresh.setOnRefreshListener {
                viewModel.initPaging(
                    this@MainActivity, id
                )
            }
        }
        setObservableMovie()
    }


    private fun setObservableMovie() {
        viewModel.popular.observe(this) {
            when (it) {
                is VmData.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                    binding.rvMovie.visibility = View.GONE
                    binding.imgAlert.visibility = View.GONE
                    binding.tvAlert.visibility = View.GONE
                }

                is VmData.Empty -> {
                    binding.rvMovie.visibility = View.GONE
                    binding.imgAlert.visibility = View.VISIBLE
                    binding.tvAlert.visibility = View.VISIBLE
                }

                is VmData.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    binding.rvMovie.visibility = View.VISIBLE
                    binding.imgAlert.visibility = View.GONE
                    binding.tvAlert.visibility = View.GONE
                }

                is VmData.Failure -> {
                    binding.swipeRefresh.isRefreshing = false
                    binding.rvMovie.visibility = View.GONE
                    binding.imgAlert.visibility = View.GONE
                    binding.tvAlert.visibility = View.GONE
                }
            }
            viewModel.data.observe(this, adapter::submitList)
            binding.rvMovie.adapter = adapter
            binding.rvMovie.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun getDetail(data: ResultsItem) {
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra("id", data.id)
        intent.putExtra("title", data.title)
        intent.putExtra("original_title", data.originalTitle)
        intent.putExtra("image", "https://image.tmdb.org/t/p/w185" + data.backdropPath)
        intent.putExtra("poster", "https://image.tmdb.org/t/p/w185" + data.posterPath)
        intent.putExtra("overview", data.overview)
        intent.putExtra("rilis", data.releaseDate)
        intent.putExtra("vote", data.voteAverage.toString())
        startActivity(intent)
    }
}