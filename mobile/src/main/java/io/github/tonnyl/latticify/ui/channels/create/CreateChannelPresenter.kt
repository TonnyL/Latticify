package io.github.tonnyl.latticify.ui.channels.create

import io.github.tonnyl.latticify.data.repository.ChannelsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class CreateChannelPresenter(
        private val mView: CreateChannelContract.View,
        private val mCreateAsChannel: Boolean
) : CreateChannelContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    companion object {
        const val KEY_CREATE_CHANNEL = "KEY_CREATE_CHANNEL"
    }

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun createChannelOrGroup(asChannel: Boolean, channelName: String) {
        val disposable = ChannelsRepository.create(channelName, true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
                        mView.showChannelOrGroupCreated(it.channel)
                    }
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

}