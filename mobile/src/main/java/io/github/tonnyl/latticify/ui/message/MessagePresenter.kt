package io.github.tonnyl.latticify.ui.message

import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.Message
import io.github.tonnyl.latticify.data.repository.ChatRepository
import io.github.tonnyl.latticify.data.repository.PinsRepository
import io.github.tonnyl.latticify.data.repository.StarredItemsRepository
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

    override fun updateMessage(content: String, message: Message) {
        val disposable = ChatRepository.update(mChannel.id, content, message.ts, true, message.attachments.toString(), false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
//                        mView.dismissMessageAction()
                    }
                }, {
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun starMessage(timestamp: String, starred: Boolean) {
        val disposable = (if (starred) StarredItemsRepository.add(mChannel.id, "", "", timestamp = timestamp) else StarredItemsRepository.remove(mChannel.id, "", "", timestamp))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
//                        mView.showMessageStarred(starred)
                    }
                }, {
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun copyLinkToMessage(messageTimestamp: String) {
        val disposable = ChatRepository.getPermalink(mChannel.id, messageTimestamp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok && it.permalink != null) {
                        mView.copyLink(it.permalink)
                    } else {
                        it.error?.let {
                            mView.displayMessage(it)
                        }
                    }
                }, {
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun deleteMessage(messageTimestamp: String) {
        val disposable = ChatRepository.delete(mChannel.id, messageTimestamp, true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {
                    it.printStackTrace()

                    it.message?.let {
                        mView.displayMessage(it)
                    }
                })
        mCompositeDisposable.add(disposable)
    }

    override fun pinMessage(timestamp: String, toPin: Boolean) {
        val disposable = (if (toPin) PinsRepository().add(mChannel.id, "", "", timestamp) else PinsRepository().remove(mChannel.id, "", "", timestamp))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {

                    }
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

}