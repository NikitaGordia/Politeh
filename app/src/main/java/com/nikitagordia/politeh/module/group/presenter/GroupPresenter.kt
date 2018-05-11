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

    override fun updateData(force: Boolean) {
        if (force) {
            if (loadingStarted) source.cancel(this)
            groups.value = MetaLiveGroup(mutableListOf(), listOf(), 0)
            source.subscribeOnGroup(this)
            loadingStarted = true
        } else {
            if (!loadingStarted) {
                loadingStarted = true
                source.subscribeOnGroup(this)
            }
        }
    }

    override fun onDataGroup(list: List<Group>, percent: Int) {
        if (percent == -1) {
            groups.value = MetaLiveGroup(mutableListOf(), list, -1)
            return
        }
        val all = groups.value?.all ?: mutableListOf()
        all.addAll(list)
        val res = groups.value ?: MetaLiveGroup(all, list, percent)
        res.intermediate = list
        res.percent = percent
        groups.value = res
    }
}