package com.nikitagordia.politeh.module.group.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.WindowManager
import com.nikitagordia.politeh.R
import com.nikitagordia.politeh.module.group.presenter.GroupPresenter
import com.nikitagordia.politeh.module.group.view.list.GroupAdapter
import com.nikitagordia.politeh.repository.remote.model.Group
import com.nikitagordia.politeh.util.flex
import kotlinx.android.synthetic.main.activity_group.*


class GroupActivity : AppCompatActivity(), GroupViewInterface {

    val adapter = GroupAdapter(this)

    val presenter = GroupPresenter()

    var updated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_group)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter

        search.setOnClickListener{ search.setIconified(false) }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (updated) updated = false; else submitSearchQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (updated) updated = false; else submitSearchQuery(newText)
                return true
            }
        })

        presenter.attach(this)
    }

    fun submitSearchQuery(query: String?) {
        query?.apply {
            val res = flex(query)
            updated = true
            search.setQuery(res, false)
            adapter.updateQuery(res)
        }
    }

    override fun onDataGroup(list: List<Group>) {
        adapter.add(list)
    }
}