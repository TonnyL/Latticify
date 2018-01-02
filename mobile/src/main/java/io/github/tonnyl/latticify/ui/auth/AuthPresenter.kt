package io.github.tonnyl.latticify.ui.auth

import io.github.tonnyl.latticify.data.repository.AccessTokenRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 19/09/2017.
 */
class AuthPresenter(private val mView: AuthContract.View) : AuthContract.Presenter {

    private val mCompositeDisposable = CompositeDisposable()

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun requestAccessToken(code: String) {
        mCompositeDisposable.clear()
        val disposable = AccessTokenRepository.getAccessToken(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ token ->
                    mView.showMessage(token.accessToken)

                    mView.mockStoreAccessToken(token)
                }, {
                    it.printStackTrace()
                    it.message?.let {
                        mView.showMessage(it)
                    }
                })
        mCompositeDisposable.add(disposable)
    }

}