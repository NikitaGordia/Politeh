package com.nikitagordia.politeh.module.group.model.repository

import com.nikitagordia.politeh.module.group.model.data.Group


/**
 * Created by nikitagordia on 5/9/18.
 */

interface SubscriberGroupInterface {

    fun onDataGroup(list: List<Group>, percent: Int)
}