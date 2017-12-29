package io.github.tonnyl.latticify.ui.search

import io.github.tonnyl.latticify.data.repository.SearchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 13/10/2017.
 */
class SearchPresenter(private val mView: SearchContract.View) : SearchContract.Presenter {

    private val mCompositeDisposable = CompositeDisposable()
    private val mSearchRepository = SearchRepository()

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun query(query: String) {
        val d1 = mSearchRepository.files(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {

                })

        val d2 = mSearchRepository.messages(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {

                })
        mCompositeDisposable.addAll(d1, d2)
    }

}