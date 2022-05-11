package com.example.fragments

import CharacterListFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity(), CharacterListFragment.OnListSelected {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel =
            ViewModelProvider(this, defaultViewModelProviderFactory).get(MainViewModel::class.java)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.container_root, CharacterListFragment(),
                    "CharacterList"
                )
                .commit()
        }
    }

    override fun onSelected(character: Character) {
        viewModel.addCharacter(character)
        val fragment = CharacterDetailFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_root, fragment, "fragmentDetail")
            .addToBackStack(null)
            .commit()
    }
}