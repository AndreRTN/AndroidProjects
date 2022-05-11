package com.example.ifoodui

import android.view.ViewGroup
import co.tiagoaguiar.atway.ui.adapter.ATViewHolder
import com.example.ifoodui.databinding.CategoryItemBinding

class CategoryView(viewGroup: ViewGroup): ATViewHolder<Category, CategoryItemBinding>(CategoryItemBinding::inflate,viewGroup) {
    override fun bind(item: Category) {
        binding.txtCategory.text = item.name
    }
}