package cz.kobrifed.quizy.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizService {
    @GET("questions")
    fun getQuestion(
        @Query("limit") limit: Int,
        @Query("region") region: String,
        @Query("difficulty") difficulty: String
    ): Call<Array<Question>>
}