package io.github.tonnyl.latticify.ui.chat

import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.Message
import io.github.tonnyl.latticify.mvp.BasePresenter
import io.github.tonnyl.latticify.mvp.BaseView

/**
 * Created by lizhaotailang on 07/10/2017.
 */
interface ChatContract {

    interface View : BaseView<Presenter> {

        var ignoreScrollChange: Boolean

        fun showData(epoxyModels: Collection<EpoxyModel<*>>)

        fun showDataOfNextPage(epoxyModels: Collection<EpoxyModel<*>>)

        fun addModel(epoxyModel: EpoxyModel<*>)

        fun setLoadingIndicator(loading: Boolean)

        fun showLoadingMore(loadingMore: Boolean)

        fun showEmptyView()

        fun showErrorView()

        fun showChannel(channel: Channel)

        fun gotoMessageDetails(message: Message)

        fun gotoChannelDetails(channel: Channel)

    }

    interface Presenter : BasePresenter {

        var mOldestMessageTs: String

        var mHasMore: Boolean

        fun fetchData()

        fun fetchDataOfNextPage()

        fun generateEpoxyModels(dataList: List<*>): Collection<EpoxyModel<*>>

        fun connectWebSocket()

        fun viewDetails()

        fun sendMessage(content: String)

    }

}