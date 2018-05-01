package io.github.tonnyl.latticify.ui.search.files

import android.content.Intent
import android.view.View
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.data.File
import io.github.tonnyl.latticify.data.Pagination
import io.github.tonnyl.latticify.data.repository.SearchRepository
import io.github.tonnyl.latticify.epoxy.SearchedFileModel_
import io.github.tonnyl.latticify.mvp.ListContract
import io.github.tonnyl.latticify.mvp.ListPresenter
import io.github.tonnyl.latticify.ui.file.FileActivity
import io.github.tonnyl.latticify.ui.file.FilePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 29/12/2017.
 */
class SearchFilesPresenter(mView: ListContract.View) : ListPresenter(mView) {

    override var mCursor: String = ""

    private val mCompositeDisposable = CompositeDisposable()
    private val mSearchRepository = SearchRepository()
    private var mCachedFiles = mutableListOf<File>()

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
        if (mPagination == null
                || mPagination?.page == mPagination?.pageCount
                || mQueryWords.isEmpty()) {
            return
        }
        mView.showLoadingMore(true)

        val disposable = mSearchRepository.files(query = mQueryWords, page = mPagination!!.page + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapper ->
                    mView.showLoadingMore(false)

                    if (wrapper.ok && wrapper.files.matches?.isNotEmpty() == true) {
                        mCachedFiles.clear()
                        mCachedFiles.addAll(wrapper.files.matches)

                        mView.showData(generateEpoxyModels(mCachedFiles))

                        mPagination = wrapper.files.pagination
                    }
                }, {
                    it.printStackTrace()
                    mView.showLoadingMore(false)
                })
        mCompositeDisposable.addAll(disposable)
    }

    override fun generateEpoxyModels(dataList: List<*>): Collection<EpoxyModel<*>> {
        return dataList.filter { it is File }
                .map { file ->
                    SearchedFileModel_()
                            .file(file as File)
                            .itemClickListener(View.OnClickListener {
                                (mView as SearchFilesFragment).context?.let {
                                    it.startActivity(Intent(it, FileActivity::class.java)
                                            .apply { putExtra(FilePresenter.KEY_EXTRA_FILE, file) })
                                }
                            })
                }
    }

    fun fetchSearchResults(queryWords: String) {
        if (queryWords.isEmpty()) {
            return
        }
        mQueryWords = queryWords

        val disposable = mSearchRepository.files(queryWords)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapper ->
                    if (wrapper.ok) {
                        wrapper.files.matches?.let {
                            if (it.isNotEmpty()) {
                                mCachedFiles.clear()
                                mCachedFiles.addAll(it)

                                mView.showData(generateEpoxyModels(mCachedFiles))
                            } else {
                                mView.showEmptyView()
                            }

                            mPagination = wrapper.files.pagination
                        }
                    } else {
                        mView.showErrorView()
                    }
                }, {
                    it.printStackTrace()
                    mView.setLoadingIndicator(false)
                    mView.showErrorView()
                })
        mCompositeDisposable.addAll(disposable)
    }

}