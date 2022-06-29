package com.test.movieapp.ui.activity.home

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.oratakashi.viewbinding.core.binding.recyclerview.ViewHolder
import com.oratakashi.viewbinding.core.binding.recyclerview.viewBinding
import com.test.movieapp.data.model.movie.ResultsItem
import com.test.movieapp.databinding.ItemMovieBinding

class MovieAdapter(
    private val onClick: (ResultsItem) -> Unit
) : PagedListAdapter<ResultsItem, ViewHolder<ItemMovieBinding>>(
    DIFF_CALLBACK
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<ItemMovieBinding> = viewBinding(parent)

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder<ItemMovieBinding>, position: Int) {
        with(holder.binding) {
            getItem(position)?.let {
                Glide.with(imgMovie.context)
                    .load("https://image.tmdb.org/t/p/w185" + it.backdropPath)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(imgMovie)
                tvTitle.text = it.title
                tvDescription.text = it.originalTitle

            }

            holder.itemView.setOnClickListener {
                onClick(getItem(position)!!)
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<ResultsItem>() {
            override fun areItemsTheSame(
                oldItem: ResultsItem,
                newItem: ResultsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResultsItem,
                newItem: ResultsItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}