package com.android.app.network

import io.reactivex.Observable
import retrofit2.http.*


interface ReviewService {
    companion object {
        const val berlin = "berlin-l17/"
        const val templeHof = "tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/"
    }

    @Headers("User-Agent: Your-App-Name")
    @GET(berlin + templeHof + "reviews.json")
    fun all(@Query("page") page : Int,
            @Query("count") count : Int,
            @Query("sortBy=date_of_review&direction=") sort : String): Observable<ReviewResponse>


    @POST(berlin + templeHof + "new")
    fun createReview(@Header("token") token: String, //a token received during login
                     // the user session has information about the author
                     // so the server already knows name, type, etc
                     // we just need to send data about the review
                     @Body user: PostReviewBody): Observable<PostReviewResponse>

}