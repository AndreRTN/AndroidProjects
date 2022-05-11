package com.example.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.BaseProgressIndicator
import com.google.android.material.progressindicator.CircularProgressIndicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var progressIndicator: CircularProgressIndicator
    private val postAdapter: PostAdapter = PostAdapter()
    private lateinit var recyclerPost: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressIndicator = findViewById(R.id.progress_indicator)
        recyclerPost = findViewById(R.id.rv_posts)

        recyclerPost.layoutManager = LinearLayoutManager(this)

        recyclerPost.adapter = postAdapter
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getList()
        observer()


    }

    private fun observer() {

        viewModel.postList.observe(this, Observer {
            it.let {
            postAdapter.attachList(it)
            }
        })

        viewModel.hasLoading.observe(this, Observer {

            when(it) {
                true -> progressIndicator.visibility = View.VISIBLE
                false -> progressIndicator.visibility = View.GONE
                else -> progressIndicator.visibility = View.VISIBLE
            }

        })
    }
}