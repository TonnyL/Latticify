package io.github.tonnyl.latticify.ui.channel.edit

import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.mvp.BasePresenter
import io.github.tonnyl.latticify.mvp.BaseView

/**
 * Created by lizhaotailang on 14/10/2017.
 */
interface EditChannelContract {

    interface View : BaseView<Presenter> {

        fun showChanelInfo(channel: Channel)

        fun showUpdateError()

        fun showError(errorMessage: String)

        fun showUpdateSuccess()

    }

    interface Presenter : BasePresenter {

        fun update(name: String, purpose: String, topic: String)

    }

}