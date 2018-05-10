package com.nikitagordia.politeh.module.group.model.repository

/**
 * Created by nikitagordia on 5/9/18.
 */
interface SourceGroupInterface {

    fun subscribeOnGroup(sub : SubscriberGroupInterface)

    fun cancel(sub : SubscriberGroupInterface)
}