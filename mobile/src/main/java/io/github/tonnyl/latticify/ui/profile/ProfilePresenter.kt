package io.github.tonnyl.latticify.ui.profile

import io.github.tonnyl.latticify.data.User
import io.github.tonnyl.latticify.data.repository.IMRepository
import io.github.tonnyl.latticify.data.repository.UsersRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 08/10/2017.
 */
class ProfilePresenter(private val mView: ProfileContract.View) : ProfileContract.Presenter {

    private var mUser: User? = null
    private var mUserId: String? = null
    private val mCompositeDisposable = CompositeDisposable()

    constructor(view: ProfileContract.View, userId: String) : this(view) {
        mUserId = userId
    }

    constructor(view: ProfileContract.View, user: User) : this(view) {
        mUser = user
        mUserId = mUser?.id
    }

    companion object {
        @JvmField
        val KEY_EXTRA_USER = "KEY_EXTRA_USER"
        @JvmField
        val KEY_EXTRA_USER_ID = "KEY_EXTRA_USER_ID"
    }

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {
        mUser?.let {
            mView.showData(it)
        } ?: run {
            fetchData()
        }

//        mView.setIsMe(mUserId == AccessTokenManager.getAccessToken().userId)
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun fetchData() {
        val disposable = UsersRepository.info(userId = mUserId ?: "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
                        mView.showData(it.user)
                    }
                }, {
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun openIm() {
        mUserId?.let {
            val disposable = IMRepository.open(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.ok) {
                            mView.gotoChannel(it.channel)
                        }
                    }, {
                        it.printStackTrace()
                    })
            mCompositeDisposable.add(disposable)
        }
    }

}