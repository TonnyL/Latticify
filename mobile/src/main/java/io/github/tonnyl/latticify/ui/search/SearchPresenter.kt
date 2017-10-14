package io.github.tonnyl.latticify.ui.search

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.data.SearchMessageMatch
import io.github.tonnyl.latticify.epoxy.SearchMessageModel_
import io.github.tonnyl.latticify.mvp.ListContract
import io.github.tonnyl.latticify.mvp.ListPresenter

/**
 * Created by lizhaotailang on 13/10/2017.
 */
class SearchPresenter(mView: ListContract.View) : ListPresenter(mView) {

    override var mCursor: String = ""

    override fun subscribe() {

    }

    override fun unsubscribe() {

    }

    override fun fetchData() {

    }

    override fun fetchDataOfNextPage() {

    }

    override fun generateEpoxyModels(dataList: List<*>): Collection<EpoxyModel<*>> {
        return dataList.filter { it is SearchMessageMatch }
                .map {
                    SearchMessageModel_()
                            .message(it as SearchMessageMatch)
                            .itemClickListener(View.OnClickListener {

                            })
                }
    }

}