package io.github.tonnyl.latticify.ui.groups

import android.content.Intent
import android.view.View
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.repository.GroupsRepository
import io.github.tonnyl.latticify.epoxy.ChannelModel_
import io.github.tonnyl.latticify.mvp.ListContract
import io.github.tonnyl.latticify.mvp.ListPresenter
import io.github.tonnyl.latticify.ui.chat.ChatActivity
import io.github.tonnyl.latticify.ui.chat.ChatPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 12/10/2017.
 */
class GroupsPresenter(mView: ListContract.View) : ListPresenter(mView) {

    override var mCursor: String = ""

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun subscribe() {
        mView.setLoadingIndicator(true)

        fetchData()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun fetchData() {
        val disposable = GroupsRepository.list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mView.setLoadingIndicator(false)

                    if (it.ok) {
                        if (it.groups.isNotEmpty()) {
                            mView.showData(generateEpoxyModels(it.groups))
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
            val disposable = GroupsRepository.list(mCursor)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mView.showLoadingMore(false)

                        if (it.ok && it.groups.isNotEmpty()) {
                            mView.showDataOfNextPage(generateEpoxyModels(it.groups))
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
                it is Channel
                        && it.isGroup == true
                        && it.isArchived != true
            }.map { channel ->
                ChannelModel_()
                        .channel(channel as Channel)
                        .itemOnClickListener(View.OnClickListener {
                            mView.gotoActivity(Intent(it.context, ChatActivity::class.java).apply { putExtra(ChatPresenter.KEY_EXTRA_CHANNEL, channel) })
                        })
            }

}