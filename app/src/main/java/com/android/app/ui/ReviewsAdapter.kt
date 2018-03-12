package com.android.app.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.app.databinding.ReviewItemBinding
import com.android.app.model.Review


class ReviewsAdapter : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>(){

    var reviews: MutableList<Review> = arrayListOf<Review>()

    fun setData(_reviews: Collection<Review>) {
        reviews.clear()
        reviews.addAll(_reviews)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ReviewItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.review = reviews[position]
        holder.view.position = position
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    class ViewHolder(val view: ReviewItemBinding) : RecyclerView.ViewHolder(view.root)
}