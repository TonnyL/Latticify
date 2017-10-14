package io.github.tonnyl.latticify.ui.channel.notifications

import io.github.tonnyl.latticify.mvp.BasePresenter
import io.github.tonnyl.latticify.mvp.BaseView

/**
 * Created by lizhaotailang on 14/10/2017.
 */
interface NotificationsContract {

    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter

}