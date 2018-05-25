package com.nikitagordia.politeh.module.main.model.data.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by nikitagordia on 2/23/18.
 */

class RoomDB : RealmObject() {

    @PrimaryKey
    var roomId: String? = null

    var roomName: String? = null

    var roomLatitude: String? = null

    var roomLongitude: String? = null

}
