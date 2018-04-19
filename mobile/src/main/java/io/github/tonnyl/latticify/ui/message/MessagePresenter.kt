package io.github.tonnyl.latticify.ui.message

import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.Message
import io.github.tonnyl.latticify.data.repository.UserPoolRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 08/10/2017.
 */
class MessagePresenter(

        private val mView: MessageContract.View,
        private val mMessage: Message,
        private val mChannel: Channel

) : MessageContract.Presenter {

    private val mCompositeDisposable = CompositeDisposable()

    companion object {
        val KEY_EXTRA_MESSAGE = "KEY_EXTRA_MESSAGE"
        val KEY_EXTRA_CHANNEL = "KEY_EXTRA_CHANNEL"
    }

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {
        mView.showMessage(mMessage)

        mView.showChannel(mChannel)

        if (mMessage.icons == null) {
            getUserInfo()
        }
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun addAReply(message: String) {

    }

    private fun getUserInfo() {
        mMessage.user?.let {
            val disposable = UserPoolRepository.getUser(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mView.showUser(it)
                    }, {

                    })
            mCompositeDisposable.add(disposable)
        }
    }

}