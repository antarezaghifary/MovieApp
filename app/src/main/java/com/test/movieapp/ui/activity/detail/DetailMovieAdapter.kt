package com.test.movieapp.ui.activity.detail

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.oratakashi.viewbinding.core.binding.recyclerview.ViewHolder
import com.oratakashi.viewbinding.core.binding.recyclerview.viewBinding
import com.test.movieapp.R
import com.test.movieapp.databinding.ItemReviewBinding
import com.test.movieapp.util.DateUtil

class DetailMovieAdapter :
    PagedListAdapter<com.test.movieapp.data.model.review.ResultsItem, ViewHolder<ItemReviewBinding>>(
        DIFF_CALLBACK
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<ItemReviewBinding> = viewBinding(parent)

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder<ItemReviewBinding>, position: Int) {
        with(holder.binding) {
            getItem(position)?.let {

                val url = it.authorDetails?.avatarPath
                val remove = 1

                Glide.with(imgProfile.context)
                    .load(url?.substring(remove))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .centerCrop()
                    .into(imgProfile)

                tvNama.text = it.author
                tvTanggal.text = DateUtil().dateFormat(it.createdAt)
                tvReview.text = it.content

            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<com.test.movieapp.data.model.review.ResultsItem>() {
            override fun areItemsTheSame(
                oldItem: com.test.movieapp.data.model.review.ResultsItem,
                newItem: com.test.movieapp.data.model.review.ResultsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: com.test.movieapp.data.model.review.ResultsItem,
                newItem: com.test.movieapp.data.model.review.ResultsItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}