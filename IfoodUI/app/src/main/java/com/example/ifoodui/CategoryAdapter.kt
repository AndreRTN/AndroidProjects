package com.example.ifoodui

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CategoryAdapter(private val listCategory: MutableList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(category: Category) {
            itemView.findViewById<TextView>(R.id.txt_category).text = category.name
            Picasso.get().load(category.logoUrl).into(itemView.findViewById<ImageView>(R.id.img_category))
            val shape = GradientDrawable()
            shape.cornerRadius = 10f
            shape.setColor(category.color.toInt())
            itemView.findViewById<View>(R.id.bg_category).background = shape
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)

    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        return holder.bind(listCategory[position])
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }
}