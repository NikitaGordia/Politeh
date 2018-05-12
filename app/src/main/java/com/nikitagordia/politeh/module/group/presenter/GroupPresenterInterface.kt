package com.nikitagordia.politeh.module.group.presenter

import android.arch.lifecycle.MutableLiveData
import com.nikitagordia.politeh.module.group.model.data.Group

interface GroupPresenterInterface {

    val groups: MutableLiveData<MetaLiveGroup>

    var state: LoadingDataState

    fun updateData(force: Boolean)
}

class MetaLiveGroup(val all: MutableList<Group>, var intermediate: List<Group>, var percent: Int)

enum class LoadingDataState { DONE, LOADING, FAILED, RAW }