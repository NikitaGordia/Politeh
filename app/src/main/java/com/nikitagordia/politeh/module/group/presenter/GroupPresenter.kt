package com.nikitagordia.politeh.module.group.presenter

import com.nikitagordia.politeh.module.group.view.GroupViewInterface
import com.nikitagordia.politeh.repository.remote.SubscriberInterface
import com.nikitagordia.politeh.repository.remote.apirozkladorgua.RetrofitImpl
import com.nikitagordia.politeh.repository.remote.model.Group

/**
 * Created by nikitagordia on 5/9/18.
 */

class GroupPresenter : GroupPresenterInterface, SubscriberInterface {

    var view: GroupViewInterface? = null

    override fun attach(child: GroupViewInterface) {
        view = child
        RetrofitImpl.subscribeOnGroup(this)
    }

    override fun onDataGroup(list: List<Group>) {
        view?.onDataGroup(list)
    }
}