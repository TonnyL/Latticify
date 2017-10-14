package io.github.tonnyl.latticify.ui.channels

import android.content.Intent
import android.view.View
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.repository.ConversationsRepository
import io.github.tonnyl.latticify.epoxy.ChannelModel_
import io.github.tonnyl.latticify.mvp.ListContract
import io.github.tonnyl.latticify.mvp.ListPresenter
import io.github.tonnyl.latticify.ui.channel.ChannelActivity
import io.github.tonnyl.latticify.ui.channel.ChannelPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 24/09/2017.
 */
class ChannelsPresenter(mView: ListContract.View) : ListPresenter(mView) {

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
        val disposable = ConversationsRepository.list(types = "private_channel,public_channel")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mView.setLoadingIndicator(false)
                    if (it.ok) {
                        with(it.channels) {
                            if (this.isNotEmpty()) {
                                mView.showData(generateEpoxyModels(this))
                            } else {
                                mView.showEmptyView()
                            }
                        }
                    } else {
                        mView.showErrorView()
                    }

                    it.responseMetaData?.let {
                        mCursor = it.nextCursor
                    }
                }, {
                    it.printStackTrace()
                    mView.showErrorView()
                    mView.setLoadingIndicator(false)
                })
        mCompositeDisposable.add(disposable)
    }

    override fun fetchDataOfNextPage() {
        if (mCursor.isNotEmpty()) {
            mView.showLoadingMore(true)
            val disposable = ConversationsRepository.list(cursor = mCursor, types = "private_channel,public_channel")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mView.showLoadingMore(false)

                        if (it.ok && it.channels.isNotEmpty()) {
                            mView.showDataOfNextPage(generateEpoxyModels(it.channels))
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
                        && it.isIm == false
                        && it.isArchived == false
                        && it.isMember == true
            }.map { channel ->
                ChannelModel_()
                        .channel(channel as Channel)
                        .itemOnClickListener(View.OnClickListener {
                            with(it.context) {
                                mView.gotoActivity(Intent(this, ChannelActivity::class.java)
                                        .apply { putExtra(ChannelPresenter.KEY_EXTRA_CHANNEL, channel) })
                            }
                        })
            }
}