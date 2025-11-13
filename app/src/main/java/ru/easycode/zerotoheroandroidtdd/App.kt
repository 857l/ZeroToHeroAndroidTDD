package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.google.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: SimpleService = retrofit.create(SimpleService::class.java)
        viewModel = MainViewModel(
            LiveDataWrapper.Base(),
            Repository.Base(
                service,
                "https://github.com/JohnnySC/ZeroToHeroAndroidTDD/blob/task/018-clouddatasource/app/sampleresponse.json"
            )
        )
    }
}