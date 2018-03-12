package com.android.app

import android.app.Application
import com.android.app.injection.AppComponent
import com.android.app.injection.DaggerAppComponent
import com.android.app.injection.ReviewModule


class MyApplication : Application() {

companion object {
    lateinit var reviewComponent: AppComponent
}

    override fun onCreate() {
        super.onCreate();
        reviewComponent = DaggerAppComponent
                .builder()
                .reviewModule(ReviewModule(this))
                .build()
    }

}