package io.github.tonnyl.latticify.ui.profile

import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.data.UsersProfile
import io.github.tonnyl.latticify.mvp.BasePresenter
import io.github.tonnyl.latticify.mvp.BaseView

/**
 * Created by lizhaotailang on 08/10/2017.
 */
interface ProfileContract {

    interface View : BaseView<Presenter> {

        fun showData(epoxyModels: Collection<EpoxyModel<*>>)

        fun showTitle(title: String)

        fun showAvatar(url: String)

    }

    interface Presenter : BasePresenter {

        fun fetchData()

        fun generateEpoxyModels(usersProfile: UsersProfile): Collection<EpoxyModel<*>>

    }

}