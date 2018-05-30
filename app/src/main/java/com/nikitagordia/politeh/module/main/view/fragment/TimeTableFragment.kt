package com.nikitagordia.politeh.module.main.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nikitagordia.politeh.R
import com.nikitagordia.politeh.module.main.presenter.MainPresenter
import com.nikitagordia.politeh.module.main.presenter.MainPresenterInterface


/**
 * Created by nikitagordia on 5/25/18.
 */

class TimeTableFragment : Fragment() {

    private lateinit var presenter: MainPresenterInterface

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.fragment_timetable, container, false)
        return v!!
    }

    companion object {
        fun getInstance() : TimeTableFragment {
            return TimeTableFragment()
        }
    }

}