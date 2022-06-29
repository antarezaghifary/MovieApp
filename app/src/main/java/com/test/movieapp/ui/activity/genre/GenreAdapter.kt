package com.test.movieapp.ui.activity.genre

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oratakashi.viewbinding.core.binding.recyclerview.ViewHolder
import com.oratakashi.viewbinding.core.binding.recyclerview.viewBinding
import com.test.movieapp.data.model.popular.GenresItem
import com.test.movieapp.databinding.ItemGenreBinding

class GenreAdapter(
    private val onClick: (GenresItem) -> Unit
) : RecyclerView.Adapter<ViewHolder<ItemGenreBinding>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<ItemGenreBinding> = viewBinding(parent)

    override fun onBindViewHolder(holder: ViewHolder<ItemGenreBinding>, position: Int) {
        with(holder.binding) {
            tvGenre.text = data[position].name
            holder.itemView.setOnClickListener {
                onClick(data[position])
            }
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(data: List<GenresItem>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    private val data: MutableList<GenresItem> by lazy {
        ArrayList()
    }
}