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

        fun setIfChannelStarred(starred: Boolean)

        fun showArchiveOptions(boolean: Boolean)

        fun showLeaveOption(boolean: Boolean)

        fun showEditOption(boolean: Boolean)

        fun showChannelArchived()

        fun showLeftChannel()

    }

    interface Presenter : BasePresenter {

        fun fetchLastedInfo()

        fun fetchPinnedItems()

        fun starUnstarChannel()

        fun archiveChannel()

        fun leaveChannel()

        fun generateEpoxyModels(dataList: List<*>): Collection<EpoxyModel<*>>

    }

}