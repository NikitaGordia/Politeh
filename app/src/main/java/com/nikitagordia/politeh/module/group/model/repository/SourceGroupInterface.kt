package com.nikitagordia.politeh.module.group.model.repository

interface SourceGroupInterface {

    fun subscribeOnGroup(sub : SubscriberGroupInterface)

    fun cancel(sub : SubscriberGroupInterface)
}