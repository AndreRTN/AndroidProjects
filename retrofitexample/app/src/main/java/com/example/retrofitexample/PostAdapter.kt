package com.example.retrofitexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var list: List<PostModel> = ArrayList()
    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(post: PostModel) {
            val  postItem: TextView = itemView.findViewById(R.id.post_item)
            postItem.text = post.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun attachList(list: List<PostModel>) {
        this.list = list
        notifyDataSetChanged()
    }
}