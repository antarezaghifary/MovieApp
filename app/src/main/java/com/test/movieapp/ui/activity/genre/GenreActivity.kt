package com.test.movieapp.ui.activity.genre

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.test.movieapp.data.model.genre.GenresItem
import com.test.movieapp.databinding.ActivityGenreBinding
import com.test.movieapp.ui.activity.home.MainActivity
import com.test.movieapp.util.VmData

class GenreActivity : AppCompatActivity() {
    private val binding: ActivityGenreBinding by viewBinding()
    private val viewModel: GenreViewModel by viewModels()
    private val adapter: GenreAdapter by lazy {
        GenreAdapter {
            getMovie(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            viewModel.genres()
        }
        setObservableGenres()
    }

    private fun setObservableGenres() {
        viewModel.genre.observe(this) {
            when (it) {
                is VmData.Loading -> {

                }

                is VmData.Empty -> {
                    binding.rvGenre.visibility = View.GONE
                    binding.imgAlert.visibility = View.VISIBLE
                    binding.tvAlert.visibility = View.VISIBLE
                }

                is VmData.Success -> {
                    adapter.addAll(it.data)
                    Log.e("TAG", "setObservableGenres: ${it.data}")
                }
                is VmData.Failure -> {

                }
            }
            binding.rvGenre.adapter = adapter
            binding.rvGenre.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun getMovie(data: GenresItem) {
        Log.e("TAG", "getMovie: ${data.id}")
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("name", data.name)
        intent.putExtra("id", data.id)
        startActivity(intent)
    }
}