package com.android.app.repository

import com.android.app.model.Review
import com.android.app.network.ReviewResponse
import com.android.app.network.ReviewService
import com.android.app.persistence.ReviewDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ReviewRepositoryImpl(private val reviewDao: ReviewDao, private val reviewService: ReviewService) : ReviewRepository {

    private var reviews: MutableList<List<Review>> = mutableListOf()

    override fun fetchReviews(page: Int, descending: Boolean, pageSize: Int): Observable<ReviewResponse> {
        val sort = if (descending) "DESC" else "ASC"
        return reviewService.all(page, pageSize, sort)
                .doOnNext { storeReviewsInDb(it.data) }
    }

    override fun getFromCache(page: Int, descending: Boolean, pageSize: Int, rating: String?): Observable<List<Review>?>? {
        if (reviews.size > page) {
            return Observable.just(reviews[page])
        }
        return getFromDataBase()
                .map { it.reversed() }
                .map {
                    it.filterIndexed {
                        index, item -> index < (page+1)* pageSize && index >= page*pageSize
                    }
                }
                .doOnNext { if (!it.isEmpty()) reviews.add(it) }
    }

    private fun getFromDataBase(): Observable<List<Review>> {
        return reviewDao.getReviews().toObservable()
    }

    override fun updateReviews(data: List<Review>) {
        reviews.add(data)
        reviewDao.insertReviews(data)
    }

    private fun storeReviewsInDb(reviews: List<Review>) {
        Observable.fromCallable { updateReviews(reviews) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnError { e -> e.printStackTrace() }
                .subscribe()
    }

}