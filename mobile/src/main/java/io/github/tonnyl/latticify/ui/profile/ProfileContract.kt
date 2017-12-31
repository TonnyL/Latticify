package io.github.tonnyl.latticify.ui.profile

import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.User
import io.github.tonnyl.latticify.mvp.BasePresenter
import io.github.tonnyl.latticify.mvp.BaseView

/**
 * Created by lizhaotailang on 08/10/2017.
 */
interface ProfileContract {

    interface View : BaseView<Presenter> {

        fun showData(user: User)

        fun gotoChannel(channel: Channel)

        fun setIsMe(isMe: Boolean)

    }

    interface Presenter : BasePresenter {

        fun fetchData()

        fun openIm()

    }

}