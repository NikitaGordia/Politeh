package com.nikitagordia.politeh.module.main.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.nikitagordia.politeh.R
import com.nikitagordia.politeh.module.group.view.GroupActivity
import com.nikitagordia.politeh.module.main.presenter.MainPresenter
import com.nikitagordia.politeh.module.main.presenter.MainPresenterInterface
import com.nikitagordia.politeh.module.main.view.fragment.LoadingFragment
import com.nikitagordia.politeh.module.main.view.fragment.TimeTable
import com.nikitagordia.politeh.util.SharedPreferencesManager

class MainActivity : AppCompatActivity() {

    private lateinit var presenter: MainPresenterInterface

    private var loading = false

    private val loadingFragment = LoadingFragment.getInstance()
    private val timeTableFragment = TimeTable.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (SharedPreferencesManager.getGroupId(this) == -1) {
            startActivity(Intent(this, GroupActivity::class.java))
            finish()
            return
        }

        presenter = ViewModelProviders.of(this).get(MainPresenter::class.java)

        presenter.initLocalDB(applicationContext)
        presenter.lessons.observe(this, Observer {
            updateFragment(it != null)
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
