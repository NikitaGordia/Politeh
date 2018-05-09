package com.nikitagordia.politeh.repository.remote

/**
 * Created by nikitagordia on 5/9/18.
 */
interface SourceInterface {

    fun subscribeOnGroup(sub : SubscriberInterface)

    fun cancel(sub : SubscriberInterface)
}