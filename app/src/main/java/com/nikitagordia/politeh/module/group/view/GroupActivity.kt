package com.nikitagordia.politeh.module.group.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Resources
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import com.nikitagordia.politeh.R
import com.nikitagordia.politeh.module.group.presenter.GroupPresenter
import com.nikitagordia.politeh.module.group.presenter.GroupPresenterInterface
import com.nikitagordia.politeh.module.group.presenter.LoadingDataState
import com.nikitagordia.politeh.module.group.view.list.GroupAdapter
import com.nikitagordia.politeh.util.flex
import kotlinx.android.synthetic.main.activity_group.*


class GroupActivity : AppCompatActivity() {

    private lateinit var adapter: GroupAdapter

    private lateinit var presenter: GroupPresenterInterface
    private lateinit var bundle: Bundle

    var updated = false
    var load = false

    private val loadingAnimatorSet = AnimatorSet()

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
                    showFailed()
                    Snackbar.make(coordinator, R.string.connection_error, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, { adapter.clear(); presenter.updateData(true) }).show()
                    return@Observer
                }
                if (adapter.list.isEmpty()) adapter.add(all) else adapter.add(intermediate)
                progress.text = "${it.percent}%"
                updateLoadingView()
            }
        })
        presenter.updateData(false)

        updateLoadingView()
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

    private fun updateLoadingView() = when (presenter.state) {
        LoadingDataState.LOADING -> showLoading()
        LoadingDataState.FAILED -> showFailed()
        else -> hideLoading()
    }

    private fun showFailed() {
        load = false
        progress.text = ":("
        loadingAnimatorSet.cancel()
        loadingAnimatorSet.play(ObjectAnimator.ofFloat(progress, "alpha", progress.alpha, 1F).setDuration(300))
                .after(200)
                .before(ObjectAnimator.ofFloat(loading, "alpha", loading.alpha, 0F).setDuration(200))
        loadingAnimatorSet.start()
    }

    private fun showLoading() {
        if (load) return
        load = true
        loadingAnimatorSet.cancel()
        loadingAnimatorSet.play(ObjectAnimator.ofFloat(progress, "alpha", progress.alpha, 1F).setDuration(300))
                .after(200)
                .before(ObjectAnimator.ofFloat(loading, "alpha", loading.alpha, 1F).setDuration(200))
        loadingAnimatorSet.start()
    }
    private fun hideLoading() {
        load = false
        if (progress.alpha == 0F) return
        loadingAnimatorSet.cancel()
        loadingAnimatorSet.play(ObjectAnimator.ofFloat(progress, "alpha", progress.alpha, 0F).setDuration(300))
                .after(200)
                .before(ObjectAnimator.ofFloat(loading, "alpha", loading.alpha, 0F).setDuration(200))
        loadingAnimatorSet.start()
    }
}