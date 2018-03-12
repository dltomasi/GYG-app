package com.android.app.injection

import android.content.Context
import com.android.app.network.ReviewService
import com.android.app.network.WebClient
import com.android.app.persistence.LocalData
import com.android.app.persistence.LocalDataImpl
import com.android.app.persistence.ReviewDao
import com.android.app.persistence.ReviewsDatabase
import com.android.app.repository.ReviewRepository
import com.android.app.repository.ReviewRepositoryImpl
import com.android.app.ui.ReviewsViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ReviewModule(val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun providesDatabase(context: Context): ReviewsDatabase {
        return ReviewsDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideReviewDataSource(database: ReviewsDatabase): ReviewDao {
        return database.reviewDao()
    }

    @Provides
    @Singleton
    fun provideReviewService() : ReviewService {
        return WebClient().reviewService()
    }

    @Provides
    @Singleton
    fun provideReviewRepository(dataSource: ReviewDao, reviewService: ReviewService): ReviewRepository {
        return ReviewRepositoryImpl(dataSource, reviewService)
    }

    @Provides
    @Singleton
    fun provideReviewViewModel(reviewRepository: ReviewRepository, localData: LocalData) : ReviewsViewModel {
        return ReviewsViewModel(reviewRepository, localData)
    }

    @Provides
    @Singleton
    fun provideLocalData(activity: Context) : LocalData {
        return LocalDataImpl(activity)
    }

}
