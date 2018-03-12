
package com.android.app.persistence

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.android.app.model.Review
import io.reactivex.Single

@Dao
interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReview(review: Review)

    @Query("DELETE FROM Reviews")
    fun deleteAllReviews()

    @Query("SELECT * FROM Reviews")
    fun getReviews() : Single<List<Review>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReviews(reviews: List<Review>)
}
