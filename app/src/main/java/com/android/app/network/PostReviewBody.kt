package com.android.app.network

data class PostReviewBody(val title: String?,
                          val rating: String,
                          val message: String? = null)
