package com.android.app.ui

import com.android.app.RxImmediateSchedulerRule
import com.android.app.model.Review
import com.android.app.persistence.LocalData
import com.android.app.repository.ReviewRepository
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.internal.verification.VerificationModeFactory.times
import org.mockito.junit.MockitoJUnit

class ReviewViewModelTest {

    @Rule @JvmField
    val rule = MockitoJUnit.rule()!!

    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    private val repository: ReviewRepository = mock(ReviewRepository::class.java)
    private val localData: LocalData = mock(LocalData::class.java)

    private lateinit var viewModel: ReviewsViewModel

    @Before fun setUp() {
        viewModel = ReviewsViewModel(repository, localData)
        viewModel.pages.set(5)
        viewModel.page.set(0)
        viewModel.pageSize = 10
        viewModel.descending = true
    }

    private val REVIEW = Review(1)

    @Test
    fun test_validCache_shouldNotCallApi() {
        Mockito.`when`(repository.getFromCache(1, true, 10))
                .thenReturn(Observable.just(listOf(REVIEW)))

        viewModel.nextPage()

        Mockito.verify(repository, times(0)).fetchReviews(1, true, 10)
    }

    @Test
    fun test_emptyCache_shouldCallApi() {
        Mockito.`when`(repository.getFromCache(1, true,  10))
                .thenReturn(Observable.just(emptyList<Review>()))

        viewModel.nextPage()

        Mockito.verify(repository).fetchReviews(1, true, 10)
    }

}

