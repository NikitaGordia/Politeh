package com.nikitagordia.politeh.module.main.model.data

/**
 * Created by nikitagordia on 5/25/18.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by nikitagordia on 2/23/18.
 */

class Room {

    @SerializedName("room_id")
    @Expose
    var roomId: String? = null

    @SerializedName("room_name")
    @Expose
    var roomName: String? = null

    @SerializedName("room_latitude")
    @Expose
    var roomLatitude: String? = null

    @SerializedName("room_longitude")
    @Expose
    var roomLongitude: String? = null

}
