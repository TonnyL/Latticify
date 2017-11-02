package io.github.tonnyl.latticify.ui.channel.notifications

import io.github.tonnyl.latticify.data.Channel

/**
 * Created by lizhaotailang on 14/10/2017.
 */
class NotificationsPresenter(view: NotificationsContract.View, channel: Channel) : NotificationsContract.Presenter {

    private val mView = view
    private val mChannel = channel

    companion object {
        @JvmField
        val KEY_EXTRA_CHANNEL = "KEY_EXTRA_CHANNEL"
    }

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {

    }

}