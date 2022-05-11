package com.example.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class CharacterDetailFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = CharacterDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(
            requireActivity(),
            defaultViewModelProviderFactory
        ).get(MainViewModel::class.java)
        val view = viewModel.character.value?.let {
            val view = inflater.inflate(R.layout.fragment_detail, container, false)
            view.findViewById<ImageView>(R.id.image).setImageResource(it.imageResId)
            view.findViewById<TextView>(R.id.description).text = it.description
            view
        }
        return view

    }
}