package io.github.tonnyl.latticify.ui.message

import io.github.tonnyl.latticify.data.Message
import io.github.tonnyl.latticify.mvp.BasePresenter
import io.github.tonnyl.latticify.mvp.BaseView

/**
 * Created by lizhaotailang on 08/10/2017.
 */
interface MessageContract {

    interface View : BaseView<Presenter> {

        fun showMessage(message: Message)

    }

    interface Presenter : BasePresenter {



    }

}