package com.nikitagordia.politeh.module.group.model.repository

import com.nikitagordia.politeh.module.group.model.data.Group

interface SubscriberGroupInterface {

    fun onDataGroup(list: List<Group>, percent: Int)
}