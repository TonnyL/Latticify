package io.github.tonnyl.latticify.ui.search.messages

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.data.Pagination
import io.github.tonnyl.latticify.data.SearchedMessageMatch
import io.github.tonnyl.latticify.data.repository.SearchRepository
import io.github.tonnyl.latticify.epoxy.SearchedMessageModel_
import io.github.tonnyl.latticify.mvp.ListContract
import io.github.tonnyl.latticify.mvp.ListPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 29/12/2017.
 */
class SearchMessagesPresenter(mView: ListContract.View) : ListPresenter(mView) {

    override var mCursor: String = ""

    private val mCompositeDisposable = CompositeDisposable()
    private val mSearchRepository = SearchRepository()
    private val mCachedMessages = mutableListOf<SearchedMessageMatch>()

    private var mQueryWords = ""
    private var mPagination: Pagination? = null

    override fun subscribe() {

    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun fetchData() {

    }

    override fun fetchDataOfNextPage() {
        if (mPagination == null || mPagination?.page == mPagination?.pageCount) {
            return
        }

        val disposable = mSearchRepository.messages(query = mQueryWords, page = mPagination!!.page + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapper ->
                    mView.showLoadingMore(false)

                    if (wrapper.ok && wrapper.messages.matches.isNotEmpty()) {
                        mCachedMessages.clear()
                        mCachedMessages.addAll(wrapper.messages.matches)
                        mView.showData(generateEpoxyModels(mCachedMessages))

                        mPagination = wrapper.messages.pagination
                    }
                }, {
                    it.printStackTrace()
                    mView.showLoadingMore(false)
                })
        mCompositeDisposable.add(disposable)
    }

    override fun generateEpoxyModels(dataList: List<*>): Collection<EpoxyModel<*>> {
        return dataList.filter { it is SearchedMessageMatch }
                .map { message ->
                    SearchedMessageModel_()
                            .message(message as SearchedMessageMatch)
                            .itemClickListener(View.OnClickListener {

                            })
                }
    }

    fun fetchSearchResults(queryWords: String) {
        if (queryWords.isEmpty()) {
            return
        }
        mQueryWords = queryWords

        val disposable = mSearchRepository.messages(queryWords)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapper ->
                    mView.showLoadingMore(false)
                    if (wrapper.ok) {
                        if (wrapper.messages.matches.isNotEmpty()) {
                            mCachedMessages.clear()
                            mCachedMessages.addAll(wrapper.messages.matches)
                            mView.showData(generateEpoxyModels(mCachedMessages))

                            mPagination = wrapper.messages.pagination
                        } else {
                            mView.showEmptyView()
                        }
                    }
                }, {
                    it.printStackTrace()
                    mView.showErrorView()
                    mView.showLoadingMore(false)
                })
        mCompositeDisposable.add(disposable)
    }

}