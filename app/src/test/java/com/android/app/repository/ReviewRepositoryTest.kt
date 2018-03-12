package com.android.app.repository

import com.android.app.model.Review
import com.android.app.network.ReviewResponse
import com.android.app.network.ReviewService
import com.android.app.persistence.ReviewDao
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.*


class ReviewRepositoryTest {

    private lateinit var reviewRepository: ReviewRepository
    private lateinit var reviewDao: ReviewDao
    private lateinit var reviewService: ReviewService

    @Before
    fun setup() {
        reviewDao = mock(ReviewDao::class.java)
        reviewService = mock(ReviewService::class.java)
        reviewRepository = ReviewRepositoryImpl(reviewDao, reviewService)
    }

    private fun aRandomReview(): Review {
        return Review(Random().nextInt())
    }

    @Test
    fun test_noDataOnApi_returnsEmptyList() {
        `when`(reviewService.all(0, 10, "DESC"))
                .thenReturn(Observable.just(ReviewResponse(0, emptyList())))

        reviewRepository.fetchReviews(0, true, 10).test()
                .assertValueAt ( 0, {it.data.isEmpty()} )
    }

    @Test
    fun test_inMemory_returnsFromMemory() {
        val data = listOf(aRandomReview())
        reviewRepository.updateReviews(data)
        reviewRepository.getFromCache(0, true, 10)!!.test()
                .assertValueAt ( 0, {it == data} )
    }

    @Test
    fun test_notInMemory_returnsFromDataBase() {
        val cachedData = listOf(aRandomReview())
        `when`(reviewDao.getReviews()).thenReturn(Single.just(cachedData))

        reviewRepository.getFromCache(0, true, 10)!!.test()
                .assertValueAt(0, { it == cachedData })
    }

}