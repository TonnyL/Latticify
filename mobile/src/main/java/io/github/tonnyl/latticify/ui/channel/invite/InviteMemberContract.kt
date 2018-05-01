package io.github.tonnyl.latticify.ui.channel.invite

import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.mvp.BasePresenter
import io.github.tonnyl.latticify.mvp.BaseView

interface InviteMemberContract {

    interface View : BaseView<Presenter> {

        fun showData(epoxyModels: Collection<EpoxyModel<*>>)

        fun showDataOfNextPage(epoxyModels: Collection<EpoxyModel<*>>)

        fun addModel(epoxyModel: EpoxyModel<*>)

        fun setLoadingIndicator(loading: Boolean)

        fun showLoadingMore(loadingMore: Boolean)

        fun showEmptyView()

        fun showErrorView()

        fun updateProgress(currentSelected: Int)

        fun finish()

    }

    interface Presenter : BasePresenter {

        var mCursor: String

        fun fetchData()

        fun fetchDataOfNextPage()

        fun generateEpoxyModels(dataList: List<*>): Collection<EpoxyModel<*>>

        fun inviteMember()

    }

}