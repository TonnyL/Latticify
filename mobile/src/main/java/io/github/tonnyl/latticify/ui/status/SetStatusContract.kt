package io.github.tonnyl.latticify.ui.status

import io.github.tonnyl.latticify.mvp.BasePresenter
import io.github.tonnyl.latticify.mvp.BaseView

/**
 * Created by lizhaotailang on 11/10/2017.
 */
interface SetStatusContract {

    interface View : BaseView<Presenter> {

        fun setStatus(statusEmoji: String, status: String)

        fun showStatusUpdated()

        fun showUpdateStatusFailed(msg: String)

    }

    interface Presenter : BasePresenter {

        fun updateStatus(statusEmoji: String, status: String)

    }

}