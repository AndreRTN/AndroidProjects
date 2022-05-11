package com.example.retrofitexample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.timerTask

class MainViewModel : ViewModel() {
    private val remote = RetrofitClient.createService(PostService::class.java)
    val hasLoading: MutableLiveData<Boolean> = MutableLiveData()
    val postList: MutableLiveData<List<PostModel>> = MutableLiveData()
    fun getList() {
        hasLoading.value = true
        val call: Call<List<PostModel>> = remote.list()
        Timer().schedule(timerTask {
            call.enqueue(object : Callback<List<PostModel>> {
                override fun onResponse(
                    call: Call<List<PostModel>>,
                    response: Response<List<PostModel>>
                ) {
                    postList.value = response.body()
                    hasLoading.value = false
                }

                override fun onFailure(call: Call<List<PostModel>>, t: Throwable) {
                    val s = t.message
                    hasLoading.value = false
                }
            })
        }, 2000)
    }


}