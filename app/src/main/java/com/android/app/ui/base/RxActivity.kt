package com.android.app.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

open class RxActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()
    protected lateinit var progress: ProgressBar
    protected lateinit var rxViewModel: RxViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progress = ProgressBar(this)
        hideProgress()
    }

    protected fun addReaction(reaction: Disposable) {
        disposable.add(reaction)
    }

    override fun onStart() {
        super.onStart()
        addContentView(progress, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        addReaction(rxViewModel.progress
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{if (it) showProgress() else hideProgress()})
    }

    override fun onStop() {
        super.onStop()
        // clear all the subscriptions
        disposable.clear()
        rxViewModel.disposable.clear()
    }

    protected fun showProgress() {
        progress.visibility = VISIBLE
    }

    protected fun hideProgress() {
        progress.visibility = GONE
    }
}