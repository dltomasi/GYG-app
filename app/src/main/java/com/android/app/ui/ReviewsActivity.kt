
package com.android.app.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.android.app.MyApplication
import com.android.app.R
import com.android.app.databinding.ActivityReviewsBinding
import com.android.app.model.Review
import com.android.app.ui.base.RxActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReviewsActivity : RxActivity() {

    @Inject lateinit var viewModel: ReviewsViewModel
    private lateinit var adapter: ReviewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityReviewsBinding = DataBindingUtil.setContentView(this, R.layout.activity_reviews)
        MyApplication.reviewComponent.inject(this)
        rxViewModel = viewModel
        binding.viewmodel = viewModel
        adapter = ReviewsAdapter()
        binding.userList.setLayoutManager(LinearLayoutManager(this));
        binding.userList.adapter = adapter
        viewModel.page.set(0)
    }

    override fun onStart() {
        super.onStart()

        addReaction(viewModel.results
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({updateList(it)})
        )

        addReaction(viewModel.error
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()})
        )

        viewModel.start()
    }

    private fun updateList(reviews: List<Review>) {
        adapter.setData(reviews)
    }
}
