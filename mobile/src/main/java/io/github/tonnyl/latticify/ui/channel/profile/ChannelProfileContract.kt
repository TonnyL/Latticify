package io.github.tonnyl.latticify.ui.channel.profile

import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.mvp.BasePresenter
import io.github.tonnyl.latticify.mvp.BaseView

/**
 * Created by lizhaotailang on 12/10/2017.
 */
interface ChannelProfileContract {

    interface View : BaseView<Presenter> {

        fun showChannelDetails(channel: Channel)

        fun showPinnedItems(epoxyModels: Collection<EpoxyModel<*>>)

    }

    interface Presenter : BasePresenter {

        fun fetchPinnedItems()

        fun generateEpoxyModels(dataList: List<*>): Collection<EpoxyModel<*>>

    }

}