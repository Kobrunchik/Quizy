package cz.kobrifed.quizy

import android.app.Application
import cz.kobrifed.quizy.retrofit.QuizService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    companion object QGetter {
        lateinit var service: QuizService
    }

    override fun onCreate(){
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://the-trivia-api.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(QuizService::class.java)
    }
}