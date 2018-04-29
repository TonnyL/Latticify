package io.github.tonnyl.latticify.ui.channel.edit

import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.ChannelWrapper
import io.github.tonnyl.latticify.data.SetPurposeResultWrapper
import io.github.tonnyl.latticify.data.SetTopicWrapper
import io.github.tonnyl.latticify.data.repository.ChannelsRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 14/10/2017.
 */
class EditChannelPresenter(view: EditChannelContract.View, channel: Channel) : EditChannelContract.Presenter {

    private val mView = view
    private val mChannel = channel
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    companion object {
        @JvmField
        val KEY_EXTRA_CHANNEL = "KEY_EXTRA_CHANNEL"
    }

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {
        mView.showChanelInfo(mChannel)
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun update(name: String, purpose: String, topic: String) {
        val disposable = Observable.zip(
                ChannelsRepository.rename(mChannel.id, name).subscribeOn(Schedulers.io()),
                ChannelsRepository.setPurpose(mChannel.id, purpose).subscribeOn(Schedulers.io()),
                ChannelsRepository.setTopic(mChannel.id, topic).subscribeOn(Schedulers.io()),
                Function3<ChannelWrapper, SetPurposeResultWrapper, SetTopicWrapper, Triple<ChannelWrapper, SetPurposeResultWrapper, SetTopicWrapper>> { a1, a2, a3 ->
                    Triple(a1, a2, a3)
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.first.ok && it.second.ok && it.third.ok) {
                        mView.showUpdateSuccess()
                    } else {
                        mView.showUpdateError()
                    }
                }, {
                    it.printStackTrace()
                    it.message?.let {
                        mView.showError(it)
                    }
                })
        mCompositeDisposable.add(disposable)
    }

}