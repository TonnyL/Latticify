package io.github.tonnyl.latticify.ui.invite

import io.github.tonnyl.latticify.mvp.BasePresenter
import io.github.tonnyl.latticify.mvp.BaseView

/**
 * Created by lizhaotailang on 11/10/2017.
 */
interface InviteContract {

    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter {

        fun invite(email: String)

    }

}