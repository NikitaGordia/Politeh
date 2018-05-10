package com.nikitagordia.politeh.module.group.presenter

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.nikitagordia.politeh.module.group.model.data.Group
import com.nikitagordia.politeh.module.group.model.repository.SourceGroupInterface
import com.nikitagordia.politeh.module.group.model.repository.SubscriberGroupInterface
import com.nikitagordia.politeh.repository.remote.apirozkladorgua.RetrofitImpl

/**
 * Created by nikitagordia on 5/9/18.
 */

class GroupPresenter : SubscriberGroupInterface, GroupPresenterInterface, ViewModel() {

    override val groups = MutableLiveData<MetaLiveGroup>()

    private val source: SourceGroupInterface = RetrofitImpl

    private var loadingStarted: Boolean = false

    override fun updateData() {
        if (!loadingStarted) {
            loadingStarted = true
            source.subscribeOnGroup(this)
        }
    }

    override fun onDataGroup(list: List<Group>, percent: Int) {
        val all = groups.value?.all ?: mutableListOf()
        all.addAll(list)
        val res = groups.value ?: MetaLiveGroup(all, list, percent)
        res.intermediate = list
        res.percent = percent
        groups.value = res
    }
}