package io.github.tonnyl.latticify.ui.channel.profile

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.StarredPinnedItem
import io.github.tonnyl.latticify.data.repository.ChannelsRepository
import io.github.tonnyl.latticify.data.repository.PinsRepository
import io.github.tonnyl.latticify.data.repository.StarredItemsRepository
import io.github.tonnyl.latticify.epoxy.PinnedItemModel_
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 12/10/2017.
 */
class ChannelProfilePresenter(private val mView: ChannelProfileContract.View, channelId: String) : ChannelProfileContract.Presenter {

    private val mCompositeDisposable = CompositeDisposable()
    private val mChannelId = channelId
    private var mChannel: Channel? = null

    companion object {
        @JvmField
        val KEY_EXTRA_CHANNEL = "KEY_EXTRA_CHANNEL"
        @JvmField
        val KEY_EXTRA_CHANNEL_ID = "KEY_EXTRA_CHANNEL_ID"
    }

    init {
        mView.setPresenter(this)
    }

    constructor(view: ChannelProfileContract.View, channel: Channel) : this(view, channel.id) {
        mChannel = channel
    }

    override fun subscribe() {
        mChannel?.let {
            mView.showChannelDetails(it)
            mView.setIfChannelStarred(mChannel?.isStarred == true)
        } ?: run {
            val disposable = ChannelsRepository.info(mChannelId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.ok) {
                            mChannel = it.channel
                            mView.showChannelDetails(it.channel)
                        }
                    }, {
                        it.printStackTrace()
                    })
            mCompositeDisposable.add(disposable)
        }

        fetchPinnedItems()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun fetchPinnedItems() {
        val disposable = PinsRepository().list(mChannelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
                        mView.showPinnedItems(generateEpoxyModels(it.pinnedItems))
                    }
                }, {
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun generateEpoxyModels(dataList: List<*>): Collection<EpoxyModel<*>> {
        return dataList.filter { it is StarredPinnedItem }
                .map {
                    PinnedItemModel_()
                            .pin(it as StarredPinnedItem)
                            .itemClickListener(View.OnClickListener {

                            })
                }
    }

    override fun starUnstarChannel() {
        val disposable = if (mChannel?.isStarred == false) {
            StarredItemsRepository.add(mChannelId)
        } else {
            StarredItemsRepository.remove(mChannelId, "", "", "")
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseWrapper ->
                    mView.setIfChannelStarred(responseWrapper.ok)
                }, { error ->
                    error.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

}