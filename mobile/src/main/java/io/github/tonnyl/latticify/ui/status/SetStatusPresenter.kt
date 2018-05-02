package io.github.tonnyl.latticify.ui.status

import io.github.tonnyl.latticify.data.UsersProfile
import io.github.tonnyl.latticify.data.repository.UsersProfileRepository
import io.github.tonnyl.latticify.data.repository.UsersRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class SetStatusPresenter(private val mView: SetStatusContract.View) : SetStatusContract.Presenter {

    private val mCompositeDisposable = CompositeDisposable()
    private var mProfile: UsersProfile? = null

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {
        val disposable = UsersRepository.identity()
                .subscribeOn(Schedulers.io())
                .flatMap {
                    if (it.ok) {
                        UsersProfileRepository().getUsersProfile(false, it.user.id)
                    } else {
                        Observable.empty()
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
                        mProfile = it.userProfile

                        if (mProfile?.statusEmoji != null && mProfile?.statusText != null) {
                            mView.setStatus(mProfile?.statusEmoji!!, mProfile?.statusText!!)
                        }
                    }
                }, {

                })
        mCompositeDisposable.add(disposable)

    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun updateStatus(statusEmoji: String, status: String) {
        val disposable = UsersProfileRepository().setUsersProfile(name = "status_text", value = status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
                        mProfile = it.userProfile

                        mView.showStatusUpdated()
                    } else {
                        it.error?.let {
                            mView.showUpdateStatusFailed(it)
                        }
                    }
                }, {
                    it.printStackTrace()
                    it.message?.let {
                        mView.showUpdateStatusFailed(it)
                    }
                })
        mCompositeDisposable.add(disposable)
    }

}