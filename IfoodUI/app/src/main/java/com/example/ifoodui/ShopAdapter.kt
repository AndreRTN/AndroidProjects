package com.example.ifoodui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ShopAdapter(private val list: MutableList<Shop>) :
    RecyclerView.Adapter<ShopAdapter.ShopAdapterViewHolder>() {

    inner class ShopAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(shop: Shop) {

            Picasso.get().load(shop.bannerUrl)
                .into(itemView.findViewById<ImageView>(R.id.img_banner))

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shop_item, parent, false)
        return ShopAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopAdapterViewHolder, position: Int) {
        return holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}