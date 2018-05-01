package io.github.tonnyl.latticify.ui.starred

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.data.StarredPinnedItem
import io.github.tonnyl.latticify.data.repository.StarredItemsRepository
import io.github.tonnyl.latticify.epoxy.StarredItemModel_
import io.github.tonnyl.latticify.mvp.ListContract
import io.github.tonnyl.latticify.mvp.ListPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 09/10/2017.
 */
class StarredItemsPresenter(mView: ListContract.View) : ListPresenter(mView) {

    override var mCursor: String = ""

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private var mPage = 1

    override fun subscribe() {
        mView.setLoadingIndicator(true)
        fetchData()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun fetchData() {
        val disposable = StarredItemsRepository.list(page = 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mView.setLoadingIndicator(false)
                    if (it.ok) {
                        if (it.items.isNotEmpty()) {
                            mView.showData(generateEpoxyModels(it.items))
                        } else {
                            mView.showEmptyView()
                        }
                        mPage = it.paging.page
                    } else {
                        mView.showErrorView()
                    }
                }, {
                    it.printStackTrace()
                    mView.setLoadingIndicator(false)
                    mView.showErrorView()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun fetchDataOfNextPage() {
        mView.showLoadingMore(true)

        val disposable = StarredItemsRepository.list(page = mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mView.setLoadingIndicator(false)
                    if (it.ok && it.items.isNotEmpty()) {
                        mView.showData(generateEpoxyModels(it.items))
                    }
                    mPage = it.paging.page
                }, {
                    it.printStackTrace()
                    mView.setLoadingIndicator(false)
                })
        mCompositeDisposable.add(disposable)
    }

    override fun generateEpoxyModels(dataList: List<*>): Collection<EpoxyModel<*>> =
            dataList.filter { it is StarredPinnedItem }
                    .map { starredItem ->
                        StarredItemModel_()
                                .starredItem(starredItem as StarredPinnedItem)
                                .itemOnClickListener(View.OnClickListener {

                                })
                    }

}