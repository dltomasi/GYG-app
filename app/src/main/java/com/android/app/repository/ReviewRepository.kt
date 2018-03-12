package com.android.app.repository

import com.android.app.model.Review
import com.android.app.network.ReviewResponse
import io.reactivex.Observable

interface ReviewRepository {

    fun fetchReviews(page: Int, descending: Boolean, pageSize: Int): Observable<ReviewResponse>

    fun getFromCache(page: Int, descending: Boolean, pageSize: Int, rating: String? = null): Observable<List<Review>?>?

    fun updateReviews(data: List<Review>)
}