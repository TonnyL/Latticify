package io.github.tonnyl.latticify.ui.channels.create

import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.mvp.BasePresenter
import io.github.tonnyl.latticify.mvp.BaseView

/**
 * Created by lizhaotailang on 11/10/2017.
 */
interface CreateChannelContract {

    interface View : BaseView<Presenter> {

        fun showChannelOrGroupCreated(channel: Channel)

    }

    interface Presenter : BasePresenter {

        fun createChannelOrGroup(asChannel: Boolean, channelName: String)

    }

}