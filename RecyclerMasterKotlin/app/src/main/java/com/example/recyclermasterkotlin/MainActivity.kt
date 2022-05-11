package com.example.recyclermasterkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Size
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclermasterkotlin.databinding.ActivityMainBinding
import com.mooveit.library.Fakeit

class MainActivity : AppCompatActivity(), EmailAdapter.EmailAdapterListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var actionMode: ActionMode? = null
    private lateinit var emailAdapter: EmailAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        Fakeit.init()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val rvEmails = binding.rvMain
        emailAdapter = EmailAdapter(MockEmails.fakeEmails(), this)
        binding.fab.setOnClickListener {
            print("adicionei", )
            viewModel.addEmail()

        }

        val touchHelper =
            ItemTouchHelper(
                ItemTouchHandler(
                    0,
                    ItemTouchHelper.LEFT
                )
            )

        rvEmails.adapter = emailAdapter
        touchHelper.attachToRecyclerView(rvEmails)

        viewModel.email.observe(this) {

            it?.run {
                println("email $this")
                emailAdapter.addEmail(this)
                rvEmails.scrollToPosition(0)
            }

        }

    }

    inner class ItemTouchHandler(dragDirs: Int, swipeDirs: Int) :
        ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val from = viewHolder.adapterPosition
            val to = target.adapterPosition
            emailAdapter.swapItems(from, to)
            emailAdapter.notifyItemMoved(from, to)

            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            emailAdapter.removeEmail(viewHolder.adapterPosition)
        }
    }

    fun enableActionMode(position: Int) {
        if(actionMode == null) {
            actionMode = startSupportActionMode(object: ActionMode.Callback {
                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    return false
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    return false
                }

                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode?) {

                }
            })
        }

        val size: Int = emailAdapter.selectedItems.size()
        if(size == 0) {
            actionMode?.finish()
        } else {
            actionMode?.title = "$size "
            actionMode?.invalidate()
        }
    }

    override fun onItemClick(position: Int) {
        enableActionMode(position)
    }

    override fun onItemLongClick(position: Int) {
        TODO("Not yet implemented")
    }

}