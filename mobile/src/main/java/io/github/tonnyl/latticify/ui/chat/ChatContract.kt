package io.github.tonnyl.latticify.ui.chat

import android.content.Intent
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

        fun insertNewMessage(epoxyModel: EpoxyModel<*>, position: Int)

        fun updateMessage(epoxyModel: EpoxyModel<*>, message: Message)

        fun deleteMessage(epoxyModel: EpoxyModel<*>)

        fun showMessageActions(message: Message, channelId: String)

        fun dismissMessageAction()

        fun showMessageStarred(starred: Boolean)

        fun copyLink(url: String)

        fun displayMessage(content: String)

        fun showMessagePinned(pinned: Boolean)

    }

    interface Presenter : BasePresenter {

        var mOldestMessageTs: String

        var mLatestMessageTs: String

        var mHasMore: Boolean

        fun fetchData()

        fun fetchDataOfNextPage()

        fun generateEpoxyModels(dataList: List<*>): Collection<EpoxyModel<*>>

        fun viewDetails()

        fun sendMessage(content: String)

        fun updateMessage(content: String, message: Message)

        fun handleMessageEvent(intent: Intent)

        fun starMessage(timestamp: String, starred: Boolean)

        fun copyLinkToMessage(messageTimestamp: String)

        fun deleteMessage(messageTimestamp: String)

        fun pinMessage(timestamp: String, toPin: Boolean)

    }

}