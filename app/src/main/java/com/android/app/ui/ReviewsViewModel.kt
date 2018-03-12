package com.android.app.ui

import android.databinding.ObservableField
import com.android.app.model.Review
import com.android.app.persistence.LocalData
import com.android.app.repository.ReviewRepository
import com.android.app.ui.base.RxViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class ReviewsViewModel(private val repository: ReviewRepository, private val localData: LocalData) : RxViewModel() {

    var page = ObservableField<Int>()
    var pages = ObservableField<Int>()
    var pageSize = 3
    var total = 0;
    val results = PublishSubject.create<List<Review>>()
    val error = PublishSubject.create<Boolean>()
    var descending = true

    init {
        page.set(0)
        pages.set(0)
    }

    fun getReviews(): Observable<List<Review>?>? {
        return repository.getFromCache(page.get(), descending, pageSize)
    }

    fun nextPage() {
        if (page.get() < pages.get()) {
            page.set(page.get() + 1)
            getFromCache()
        }
    }

    private fun getFromCache() {
        val reviews = getReviews()
        if (reviews != null) {
            addReaction(reviews
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (it != null && !it.isEmpty()) {
                            results.onNext(it)
                        } else {
                            fetchReviewsReaction()
                        }
                    }
            )
        }
    }

    private fun fetchReviewsReaction() {
        results.onNext(emptyList())
        addReaction(repository.fetchReviews(page.get(), descending, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doOnComplete { hideProgress() }
                .doOnError { hideProgress() }
                .doOnNext {
                    total = it.total_reviews_comments
                    pages.set(total / pageSize)
                    localData.savePages(pages.get())
                    results.onNext(it.data)
                }
                .subscribe(
                        { results.onNext(it.data) },
                        { _ -> error.onNext(true) })
        )
    }

    fun previousPage() {
        if (page.get() > 0) {
            page.set(page.get() - 1)
            getFromCache()
        }
    }

    fun start() {
        pages.set(localData.getPages())
        if (pages.get() == 0) {
            fetchReviewsReaction()
        } else {
            getFromCache()
        }
    }

}