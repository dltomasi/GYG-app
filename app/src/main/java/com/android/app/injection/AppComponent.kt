package com.android.app.injection

import com.android.app.ui.ReviewsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ReviewModule::class])
interface AppComponent {
    fun inject(activity: ReviewsActivity)
}