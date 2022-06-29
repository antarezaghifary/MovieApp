package com.test.movieapp.ui.activity.detail

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oratakashi.viewbinding.core.binding.recyclerview.ViewHolder
import com.oratakashi.viewbinding.core.binding.recyclerview.viewBinding
import com.test.movieapp.data.model.trailer.ResultsItem
import com.test.movieapp.databinding.ItemTrailerBinding
import com.test.movieapp.util.loadImage

class TrailerAdapter(
    private val onClick: (ResultsItem) -> Unit
) : RecyclerView.Adapter<ViewHolder<ItemTrailerBinding>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<ItemTrailerBinding> = viewBinding(parent)

    override fun onBindViewHolder(holder: ViewHolder<ItemTrailerBinding>, position: Int) {
        with(holder.binding) {
            loadImage(imgTrailer, "http://img.youtube.com/vi/" + data[position].key + "/0.jpg")

            holder.itemView.setOnClickListener {
                onClick(data[position])
            }
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(data: List<ResultsItem>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    private val data: MutableList<ResultsItem> by lazy {
        ArrayList()
    }

}