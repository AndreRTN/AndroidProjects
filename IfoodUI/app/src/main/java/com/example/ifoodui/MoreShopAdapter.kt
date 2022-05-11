package com.example.ifoodui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MoreShopAdapter(private val list: MutableList<MoreShop>) :
    RecyclerView.Adapter<MoreShopAdapter.MoreShopAdapterViewHolder>() {

    inner class MoreShopAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(moreShop: MoreShop) {


            Picasso.get().load(moreShop.bannerUrl)
                .into(itemView.findViewById<ImageView>(R.id.img_banner))

            itemView.findViewById<TextView>(R.id.txt_shop).text = moreShop.text
            itemView.findViewById<TextView>(R.id.txt_star).text = moreShop.rate.toString()
            itemView.findViewById<TextView>(R.id.txt_subtitle).text =
                itemView.context.getString(
                    R.string.shop_category,
                    moreShop.category,
                    moreShop.distance
                )

            itemView.findViewById<TextView>(R.id.txt_price).text = itemView.context.getString(R.string.shop_price,
            moreShop.time, moreShop.price)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreShopAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.more_shop, parent, false)
        return MoreShopAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoreShopAdapterViewHolder, position: Int) {
        return holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}