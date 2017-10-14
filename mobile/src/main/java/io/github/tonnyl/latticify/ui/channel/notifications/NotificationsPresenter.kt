package io.github.tonnyl.latticify.ui.channel.notifications

/**
 * Created by lizhaotailang on 14/10/2017.
 */
class NotificationsPresenter(view: NotificationsContract.View) : NotificationsContract.Presenter {

    private val mView = view

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {

    }

}