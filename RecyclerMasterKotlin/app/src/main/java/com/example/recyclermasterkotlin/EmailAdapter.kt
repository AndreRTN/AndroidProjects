package com.example.recyclermasterkotlin

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclermasterkotlin.databinding.EmailItemBinding
import java.lang.reflect.Type
import java.util.*
import kotlin.math.absoluteValue
import kotlin.random.Random

class EmailAdapter(private val list: MutableList<Email>, var listener: EmailAdapterListener) :
    RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {
    lateinit var binding: EmailItemBinding
     val selectedItems = SparseBooleanArray()
     var currentPosition: Int? = null
    val randomColors = arrayListOf<Int>(
        R.color.purple_700,
        android.R.color.holo_red_dark,
        android.R.color.holo_green_light,
        android.R.color.holo_orange_light,
        android.R.color.holo_purple
    )


    inner class EmailViewHolder(private val item: EmailItemBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(email: Email) {

            item.txtDate.text = email.date
            item.txtSubject.text = email.subject
            item.txtUser.text = email.user
            item.txtPreview.text = email.preview
            item.txtIcon.text = email.user[0].toString()
            item.txtIcon.backgroundTintList =
                ContextCompat.getColorStateList(item.root.context, randomColors.random())

            val typeEmail = if (email.unread) Typeface.BOLD else Typeface.NORMAL
            item.txtUser.setTypeface(Typeface.DEFAULT, typeEmail)
            item.txtSubject.setTypeface(Typeface.DEFAULT, typeEmail)
            item.txtDate.setTypeface(Typeface.DEFAULT, typeEmail)


            val starIcon =
                if (email.stared) R.drawable.ic_baseline_star_24 else R.drawable.ic_baseline_star_border_24
            item.imgStar.setImageResource(starIcon)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        binding = EmailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            if (selectedItems.size() > 0) {
                listener.onItemClick(position)
            }
        }

        holder.itemView.setOnLongClickListener {
            listener.onItemLongClick(position)
            true
        }

        if(currentPosition == position) currentPosition = -1

        return holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun swapItems(from: Int, to: Int) {
        Collections.swap(list, from, to)
    }

    fun addEmail(email: Email) {
        list.add(0, email)
        notifyItemInserted(0)
    }

    fun removeEmail(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    interface EmailAdapterListener {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }
}