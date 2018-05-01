package io.github.tonnyl.latticify.ui.directory

import android.content.Intent
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.data.User
import io.github.tonnyl.latticify.data.repository.UsersRepository
import io.github.tonnyl.latticify.epoxy.UserModel_
import io.github.tonnyl.latticify.mvp.ListContract
import io.github.tonnyl.latticify.mvp.ListPresenter
import io.github.tonnyl.latticify.ui.profile.ProfileActivity
import io.github.tonnyl.latticify.ui.profile.ProfilePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class DirectoryPresenter(mView: ListContract.View) : ListPresenter(mView) {

    override var mCursor: String = ""

    private val mCompositeDisposable = CompositeDisposable()

    override fun subscribe() {
        mView.setLoadingIndicator(true)
        fetchData()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun fetchData() {
        val disposable = UsersRepository.list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mView.setLoadingIndicator(false)
                    if (it.ok) {
                        if (it.members.isNotEmpty()) {
                            mView.showData(generateEpoxyModels(it.members))
                        } else {
                            mView.showEmptyView()
                        }
                    } else {
                        mView.showErrorView()
                    }

                    it.responseMetaData?.let {
                        mCursor = it.nextCursor
                    }
                }, {
                    it.printStackTrace()
                    mView.setLoadingIndicator(false)
                    mView.showErrorView()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun fetchDataOfNextPage() {
        if (mCursor.isNotEmpty()) {
            mView.showLoadingMore(true)

            val disposable = UsersRepository.list(mCursor)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mView.showLoadingMore(false)

                        if (it.ok && it.members.isNotEmpty()) {
                            mView.showDataOfNextPage(generateEpoxyModels(it.members))
                        }

                        it.responseMetaData?.let {
                            mCursor = it.nextCursor
                        }
                    }, {
                        it.printStackTrace()
                        mView.showLoadingMore(false)
                    })
            mCompositeDisposable.add(disposable)
        } else {
            mView.showLoadingMore(false)
        }
    }

    override fun generateEpoxyModels(dataList: List<*>): Collection<EpoxyModel<*>> =
            dataList.filter {
                it is User && !it.deleted
            }.map { user ->
                UserModel_()
                        .user(user as User)
                        .itemOnClickListener { _, _, clickedView, _ ->
                            mView.gotoActivity(Intent(clickedView.context, ProfileActivity::class.java).apply {
                                putExtra(ProfilePresenter.KEY_EXTRA_USER, user)
                            })
                        }

            }

}