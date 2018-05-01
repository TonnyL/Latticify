package io.github.tonnyl.latticify.ui.channel.invite

import android.support.v4.content.res.ResourcesCompat
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.User
import io.github.tonnyl.latticify.data.repository.ChannelsRepository
import io.github.tonnyl.latticify.data.repository.UsersRepository
import io.github.tonnyl.latticify.epoxy.UserModel_
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ConcurrentHashMap

class InviteMemberPresenter(
        private val mView: InviteMemberContract.View,
        private val mChannelId: String
) : InviteMemberContract.Presenter {

    override var mCursor: String = ""

    private val mCompositeDisposable = CompositeDisposable()

    private val mSelectedUserIds = mutableListOf<String>()

    private val mMap = ConcurrentHashMap<String, Disposable>()

    companion object {
        const val KEY_EXTRA_CHANNEL_ID = "KEY_EXTRA_CHANNEL_ID"
    }

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {
        fetchData()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun fetchData() {
        mView.setLoadingIndicator(true)

        val disposable = UsersRepository.list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mView.setLoadingIndicator(false)

                    if (it.ok && it.members.isNotEmpty()) {
                        mView.showData(generateEpoxyModels(it.members))
                    } else {
                        mView.showEmptyView()
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

    override fun generateEpoxyModels(dataList: List<*>): Collection<EpoxyModel<*>> {
        return dataList.filter {
            it is User && !it.deleted
        }.map { user ->
            UserModel_()
                    .user(user as User)
                    .itemOnClickListener { model, parentView, clickedView, position ->
                        if (mSelectedUserIds.contains(user.id)) {
                            mSelectedUserIds.remove(user.id)

                            parentView.itemLayout?.setBackgroundResource(0)
                        } else {
                            mSelectedUserIds.add(user.id)

                            parentView.itemLayout?.setBackgroundColor(ResourcesCompat.getColor(clickedView.context.resources, R.color.charles_item_background_selected, null))
                        }

                        mView.updateProgress(mSelectedUserIds.size)
                    }
        }
    }

    override fun inviteMember() {
        mSelectedUserIds.forEach { id ->
            val disposable = ChannelsRepository.invite(mChannelId, id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mMap.remove(id)

                        if (mMap.isEmpty()) {
                            mView.finish()
                        }
                    }, {
                        it.printStackTrace()

                        mMap.remove(id)

                        if (mMap.isEmpty()) {
                            mView.finish()
                        }
                    })
            mCompositeDisposable.add(disposable)

            mMap[id] = disposable
        }
    }

}