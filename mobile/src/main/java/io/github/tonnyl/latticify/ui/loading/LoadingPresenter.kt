package io.github.tonnyl.latticify.ui.loading

import io.reactivex.disposables.CompositeDisposable

class LoadingPresenter(
        private val mView: LoadingContract.View
) : LoadingContract.Presenter {

    private val mCompositeDisposable = CompositeDisposable()
    private val mCursor = ""

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {

    }

    override fun listUsers(cursor: String) {

    }

}