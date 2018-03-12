package com.android.app.ui.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject


open class RxViewModel {

    val disposable = CompositeDisposable()
    val progress = PublishSubject.create<Boolean>()


    protected fun addReaction(reaction: Disposable) {
        disposable.add(reaction)
    }

    protected fun showProgress() {
        progress.onNext(true)
    }

    protected fun hideProgress() {
        progress.onNext(false)
    }

}