package io.github.tonnyl.latticify.ui.message

import io.github.tonnyl.latticify.data.Message

/**
 * Created by lizhaotailang on 08/10/2017.
 */
class MessagePresenter(view: MessageContract.View, message: Message) : MessageContract.Presenter {

    private val mView = view
    private val mMessage = message

    companion object {
        @JvmStatic
        val KEY_EXTRA_MESSAGE = "KEY_EXTRA_MESSAGE"
    }

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {
        mView.showMessage(mMessage)
    }

    override fun unsubscribe() {

    }

}