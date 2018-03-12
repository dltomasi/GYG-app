package com.android.app.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "reviews")
data class Review(@PrimaryKey
                  @ColumnInfo(name = "id")
                  @SerializedName("review_id")
                  val id: Int,
                  @ColumnInfo(name = "title")
                  val title: String? = null,
                  @ColumnInfo(name = "rating")
                  val rating: String? = null,
                  @ColumnInfo(name = "message")
                  val message: String? = null,
                  @ColumnInfo(name = "author")
                  val author: String? = null,
                  @ColumnInfo(name = "date")
                  val date: String? = null,
                  @ColumnInfo(name = "traveler_type")
                  val traveler_type: String? = null
)