package io.github.tonnyl.latticify.data

import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 23/09/2017.
 *
 * The prefs parameter contains default [channels] and [groups] (private channels)
 * that members of this group will be invited to upon joining.
 *
 *
 * "prefs": {
 * "channels": [
 *
 * ],
 * "groups": [
 *
 *]
 *},
 */
data class Pref(
        @SerializedName("channels")
        val channels: ArrayList<Channel>,

        @SerializedName("groups")
        val groups: ArrayList<Channel>
)