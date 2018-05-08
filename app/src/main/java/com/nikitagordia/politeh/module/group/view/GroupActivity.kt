package com.nikitagordia.politeh.module.group.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.WindowManager
import com.nikitagordia.politeh.R
import com.nikitagordia.politeh.module.group.model.Group
import kotlinx.android.synthetic.main.activity_group.*


class GroupActivity : AppCompatActivity() {

    val adapter = GroupAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_group)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter

        search.setOnClickListener{ search.setIconified(false) }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        for (i in 1..100) adapter.add(Group())
    }
}