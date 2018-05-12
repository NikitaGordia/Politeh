package com.nikitagordia.politeh.module.group.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import com.nikitagordia.politeh.R
import com.nikitagordia.politeh.module.group.presenter.GroupPresenter
import com.nikitagordia.politeh.module.group.presenter.GroupPresenterInterface
import com.nikitagordia.politeh.module.group.view.list.GroupAdapter
import com.nikitagordia.politeh.util.flex
import kotlinx.android.synthetic.main.activity_group.*


class GroupActivity : AppCompatActivity() {

    lateinit var adapter: GroupAdapter

    lateinit var presenter: GroupPresenterInterface
    lateinit var bundle: Bundle

    var updated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        adapter = GroupAdapter(this, coordinator, resources)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
        bundle = savedInstanceState ?: Bundle()

        search.setOnClickListener{ search.isIconified = false }

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

        presenter = ViewModelProviders.of(this).get(GroupPresenter::class.java)
        presenter.groups.observe(this, Observer {
            it?.apply {
                if (percent == -1) {
                    Snackbar.make(coordinator, R.string.connection_error, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, { adapter.clear(); presenter.updateData(true) }).show()
                    return@Observer
                }
                if (adapter.list.isEmpty()) adapter.add(all) else adapter.add(intermediate)
                progress.text = "${it.percent}%"
            }
        })
        presenter.updateData(false)

        loading.setOnClickListener {
            adapter.clear()
            presenter.updateData(true)
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.onRestoreInstance(bundle)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.apply { adapter.onSaveInstance(this) }
    }

    fun submitSearchQuery(query: String?) {
        query?.apply {
            val res = flex(query)
            updated = true
            search.setQuery(res, false)
            adapter.updateQuery(res)
        }
    }
}