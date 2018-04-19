package io.github.tonnyl.latticify.ui.message

import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.Message
import io.github.tonnyl.latticify.data.User
import io.github.tonnyl.latticify.mvp.BasePresenter
import io.github.tonnyl.latticify.mvp.BaseView

/**
 * Created by lizhaotailang on 08/10/2017.
 */
interface MessageContract {

    interface View : BaseView<Presenter> {

        fun showMessage(message: Message)

        fun showChannel(channel: Channel)

        fun showUser(user: User)

    }

    interface Presenter : BasePresenter {

        fun addAReply(message: String)

    }

}