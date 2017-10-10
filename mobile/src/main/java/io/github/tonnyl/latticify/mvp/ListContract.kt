package io.github.tonnyl.latticify.mvp

import com.airbnb.epoxy.EpoxyModel

/**
 * Created by lizhaotailang on 24/09/2017.
 */
interface ListContract {

    interface View : BaseView<Presenter> {

        var ignoreScrollChange: Boolean

        fun showData(epoxyModels: Collection<EpoxyModel<*>>)

        fun showDataOfNextPage(epoxyModels: Collection<EpoxyModel<*>>)

        fun addModel(epoxyModel: EpoxyModel<*>)

        fun setLoadingIndicator(loading: Boolean)

        fun showLoadingMore(loadingMore: Boolean)

        fun showEmptyView()

        fun showErrorView()

    }

    interface Presenter : BasePresenter {

        var mCursor: String

        fun fetchData()

        fun fetchDataOfNextPage()

        fun generateEpoxyModels(dataList: List<*>): Collection<EpoxyModel<*>>

    }

}