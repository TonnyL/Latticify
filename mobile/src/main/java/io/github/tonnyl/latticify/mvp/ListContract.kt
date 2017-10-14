package io.github.tonnyl.latticify.mvp

import android.content.Intent
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

        fun gotoActivity(intent: Intent)

    }

    interface Presenter : BasePresenter {

        var mCursor: String

        fun fetchData()

        fun fetchDataOfNextPage()

        fun generateEpoxyModels(dataList: List<*>): Collection<EpoxyModel<*>>

    }

}