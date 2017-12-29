package io.github.tonnyl.latticify.ui.search

import io.github.tonnyl.latticify.mvp.BasePresenter
import io.github.tonnyl.latticify.mvp.BaseView

/**
 * Created by lizhaotailang on 28/12/2017.
 */
interface SearchContract {

    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter {

        fun query(query: String)

    }

}