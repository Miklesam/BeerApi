package com.miklesam.applicationbeerapi.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.miklesam.applicationbeerapi.R
import com.miklesam.applicationbeerapi.databinding.BeerItemBinding
import com.miklesam.applicationbeerapi.models.Beer

class BeerPagingAdapter :
    PagingDataAdapter<Beer, BeerPagingAdapter.BeerViewHolder>(
        BEER_COMPARATOR
    ) {

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val binding = BeerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeerViewHolder(
            binding
        )
    }

    class BeerViewHolder(private val binding: BeerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(beer: Beer) {
            binding.apply {
                someText.text = beer.name
                Glide.with(itemView)
                    .load(beer.image_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_baseline_sync_24)
                    .into(beerImage)
            }
        }
    }

    companion object {
        private val BEER_COMPARATOR = object : DiffUtil.ItemCallback<Beer>() {
            override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean =
                oldItem == newItem
        }
    }
}
