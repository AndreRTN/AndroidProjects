package com.example.retrofitexample

import com.google.gson.annotations.SerializedName

class PostModel {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("userIdd")
    var userId: Int = 0

    @SerializedName("title")
    var title: String = ""

    @SerializedName("body")
    var body: String = ""

}