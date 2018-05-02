package io.github.tonnyl.latticify.ui.channel.members

import android.content.Intent
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.data.User
import io.github.tonnyl.latticify.data.repository.ConversationsRepository
import io.github.tonnyl.latticify.data.repository.UserPoolRepository
import io.github.tonnyl.latticify.epoxy.UserModel_
import io.github.tonnyl.latticify.mvp.ListContract
import io.github.tonnyl.latticify.mvp.ListPresenter
import io.github.tonnyl.latticify.ui.profile.ProfileActivity
import io.github.tonnyl.latticify.ui.profile.ProfilePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ChannelMembersPresenter(
        view: ListContract.View,
        private val mChannelId: String
) : ListPresenter(view) {

    override var mCursor: String = ""

    private val mCompositeDisposable = CompositeDisposable()

    companion object {
        const val KEY_EXTRA_CHANNEL_ID = "KEY_EXTRA_CHANNEL_ID"
    }

    override fun subscribe() {
        fetchData()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun fetchData() {
        mView.setLoadingIndicator(true)

        val disposable = ConversationsRepository.members(mChannelId)
                .flatMapIterable {
                    it.responseMetaData?.let {
                        mCursor = it.nextCursor
                    }
                    it.members ?: emptyList()
                }
                .concatMap { UserPoolRepository.getUser(it) }
                .toList()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    mView.setLoadingIndicator(false)

                    mView.showData(generateEpoxyModels(list))
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

    override fun fetchDataOfNextPage() {
        if (mCursor.isNotEmpty()) {
            mView.showLoadingMore(true)

            val disposable = ConversationsRepository.members(mChannelId, cursor = mCursor)
                    .flatMapIterable {
                        it.responseMetaData?.let {
                            mCursor = it.nextCursor
                        }
                        it.members ?: emptyList()
                    }
                    .concatMap { UserPoolRepository.getUser(it) }
                    .toList()
                    .toObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ list ->
                        mView.setLoadingIndicator(false)

                        mView.showData(generateEpoxyModels(list))
                    }, {

                    })
            mCompositeDisposable.add(disposable)
        }
    }

    override fun generateEpoxyModels(dataList: List<*>): Collection<EpoxyModel<*>> {
        return dataList.filter { it is User }
                .map { user ->
                    UserModel_()
                            .user(user as User)
                            .itemOnClickListener { _, _, clickedView, _ ->
                                mView.gotoActivity(Intent(clickedView.context, ProfileActivity::class.java).apply {
                                    putExtra(ProfilePresenter.KEY_EXTRA_USER, user)
                                })
                            }

                }
    }

}