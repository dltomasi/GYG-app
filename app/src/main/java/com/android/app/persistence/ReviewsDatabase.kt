
package com.android.app.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.android.app.model.Review

@Database(entities = arrayOf(Review::class), version = 1)
abstract class ReviewsDatabase : RoomDatabase() {

    abstract fun reviewDao(): ReviewDao

    companion object {

        @Volatile private var INSTANCE: ReviewsDatabase? = null

        fun getInstance(context: Context): ReviewsDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        ReviewsDatabase::class.java, "GYG.db")
                        .build()
    }
}
