package com.android.app.network

import com.android.app.model.Review


data class ReviewResponse (val total_reviews_comments: Int = 0,
                           val data: List<Review> = emptyList())
