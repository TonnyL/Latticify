package io.github.tonnyl.latticify.ui.snooze

import io.github.tonnyl.latticify.mvp.BasePresenter
import io.github.tonnyl.latticify.mvp.BaseView

/**
 * Created by lizhaotailang on 11/10/2017.
 */
interface SnoozeNotificationsContract {

    interface View : BaseView<Presenter> {

        fun updateSnoozeSwitch(enabled: Boolean)

    }

    interface Presenter : BasePresenter {

        fun endSnooze()

        fun setSnooze(minutes: Int)

    }

}