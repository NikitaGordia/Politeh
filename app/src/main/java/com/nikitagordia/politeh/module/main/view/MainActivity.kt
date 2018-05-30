package com.nikitagordia.politeh.module.main.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import com.nikitagordia.politeh.R
import com.nikitagordia.politeh.module.group.view.GroupActivity
import com.nikitagordia.politeh.module.main.presenter.MainPresenter
import com.nikitagordia.politeh.module.main.presenter.MainPresenterInterface
import com.nikitagordia.politeh.module.main.view.fragment.LoadingFragment
import com.nikitagordia.politeh.module.main.view.fragment.TimeTableFragment
import com.nikitagordia.politeh.util.SharedPreferencesManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var presenter: MainPresenterInterface

    private var loading = false

    private val loadingFragment = LoadingFragment.getInstance()
    private val timeTableFragment = TimeTableFragment.getInstance()

    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (SharedPreferencesManager.getGroupId(this) == -1) {
            startActivity(Intent(this, GroupActivity::class.java))
            finish()
            return
        }

        presenter = ViewModelProviders.of(this).get(MainPresenter::class.java)

        id = SharedPreferencesManager.getGroupId(this)
        presenter.subscribeOnLesson(id)

        presenter.lessons.observe(this, Observer {
            if (it != null) {
                if (it.list != null && !it.showLoading) updateFragment(true)
                if (it.list == null && it.showLoading) updateFragment(false)
                if (it.list != null && it.showLoading) {
                    updateFragment(false)
                    Snackbar.make(coordinator, R.string.connection_error, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, { presenter.subscribeOnLesson(id) }).show()
                }
            }
        })
    }

    fun updateFragment(done: Boolean) {
        if (!done) {
            if (!loading) {
                loading = true
                setFragment(loadingFragment)
            }
        } else {
            if (loading) {
                loading = false
                loadingFragment.loadingEnded()
            } else setFragment(timeTableFragment)
        }
    }

    private fun setFragment(newFragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, newFragment).commit()
    }
}
