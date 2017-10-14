package io.github.tonnyl.latticify.ui.channel.edit

import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.repository.ChannelsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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
        val disposable = ChannelsRepository.rename(mChannel.id, name)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap {
                    ChannelsRepository.setPurpose(mChannel.id, purpose)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap {
                    ChannelsRepository.setTopic(mChannel.id, topic)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {

                    }
                }, {
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

}