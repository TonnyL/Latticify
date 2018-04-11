package io.github.tonnyl.latticify.ui.loading

import io.github.tonnyl.latticify.mvp.BasePresenter
import io.github.tonnyl.latticify.mvp.BaseView

interface LoadingContract {

    interface View : BaseView<Presenter> {

        fun finishActivity()

    }

    interface Presenter : BasePresenter {

        fun listUsers(cursor: String)

    }

}