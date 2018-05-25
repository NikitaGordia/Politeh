package com.nikitagordia.politeh.module.group.presenter

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.nikitagordia.politeh.module.group.model.data.Group
import com.nikitagordia.politeh.module.group.model.repository.SourceGroupInterface
import com.nikitagordia.politeh.module.group.model.repository.SubscriberGroupInterface
import com.nikitagordia.politeh.repository.remote.apirozkladorgua.RetrofitImpl

class GroupPresenter : SubscriberGroupInterface, GroupPresenterInterface, ViewModel() {

    override val groups = MutableLiveData<MetaLiveGroup>()

    private val source: SourceGroupInterface = RetrofitImpl

    override var state = LoadingDataState.RAW

    override fun updateData(force: Boolean) {
        if (force) {
            if (state == LoadingDataState.LOADING) source.cancelGroup(this)
            groups.value = MetaLiveGroup(mutableListOf(), listOf(), 0)
            source.subscribeOnGroup(this)
            state = LoadingDataState.LOADING
        } else {
            if (state == LoadingDataState.RAW) {
                state = LoadingDataState.LOADING
                source.subscribeOnGroup(this)
            }
        }
    }

    override fun onDataGroup(list: List<Group>, percent: Int) {
        when (percent) {
            -1 -> {
                state = LoadingDataState.FAILED
                groups.value = MetaLiveGroup(mutableListOf(), list, -1)
                return
            }
            100 -> state = LoadingDataState.DONE
            else -> state = LoadingDataState.LOADING
        }
        val all = groups.value?.all ?: mutableListOf()
        all += list
        val res = groups.value ?: MetaLiveGroup(all, list, percent)
        res.intermediate = list
        res.percent = percent
        groups.value = res
    }
}