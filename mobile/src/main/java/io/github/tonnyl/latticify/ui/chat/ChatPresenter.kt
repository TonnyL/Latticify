package io.github.tonnyl.latticify.ui.chat

import android.content.Intent
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.ChannelWrapper
import io.github.tonnyl.latticify.data.GroupWrapper
import io.github.tonnyl.latticify.data.Message
import io.github.tonnyl.latticify.data.repository.*
import io.github.tonnyl.latticify.epoxy.MessageModel_
import io.github.tonnyl.latticify.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 06/10/2017.
 *
 */
class ChatPresenter(view: ChatContract.View, channelId: String) : ChatContract.Presenter {

    override var mOldestMessageTs: String = ""
    override var mLatestMessageTs: String = ""
    override var mHasMore: Boolean = false

    private val mCompositeDisposable = CompositeDisposable()
    private val mView = view
    private var mChannelId = channelId
    private var mChannel: Channel? = null

    private val mEpoxyModels = mutableListOf<EpoxyModel<*>>()

    companion object {
        @JvmField
        val KEY_EXTRA_CHANNEL = "KEY_EXTRA_CHANNEL"
        @JvmField
        val KEY_EXTRA_CHANNEL_ID = "KEY_EXTRA_CHANNEL_ID"
    }

    init {
        mView.setPresenter(this)
        mChannelId = channelId
    }

    constructor(view: ChatContract.View, channel: Channel) : this(view, channel.id) {
        mChannel = channel
    }

    override fun subscribe() {
        mView.setLoadingIndicator(true)

        fetchData()

        mChannel?.let {
            if (it.isChannel == true || it.isGroup == true) {
                mView.showChannel(it)
            }
        }
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun fetchData() {
        val disposable = (if (mChannel?.isGroup == true) GroupsRepository.info(mChannelId) else ChannelsRepository.info(mChannelId))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap {
                    mChannel = (it as? GroupWrapper)?.group ?: (it as ChannelWrapper).channel
                    (when {
                        mChannel?.isGroup == true -> GroupsRepository.history(mChannelId)
                        mChannel?.isChannel == true -> ChannelsRepository.history(mChannelId)
                        else -> IMRepository.history(mChannelId)
                    })
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mView.setLoadingIndicator(false)

                    if (it.ok && it.messages != null) {
                        with(it.messages) {
                            if (this.isNotEmpty()) {
                                mView.showData(generateEpoxyModels(this))
                                mOldestMessageTs = it.messages.last().ts
                                mLatestMessageTs = it.messages.first().ts
                            } else {
                                mView.showEmptyView()
                            }
                        }
                    } else {
                        mView.showErrorView()
                    }
                    mHasMore = it.hasMore ?: false
                }, {
                    it.printStackTrace()
                    mView.showErrorView()
                    mView.setLoadingIndicator(false)
                })
        mCompositeDisposable.add(disposable)
    }

    override fun fetchDataOfNextPage() {
        if (mHasMore && mOldestMessageTs.isNotEmpty()) {
            mView.showLoadingMore(true)
            val disposable = (when {
                mChannel?.isGroup == true -> GroupsRepository.history(mChannelId, latest = mOldestMessageTs)
                mChannel?.isChannel == true -> ChannelsRepository.history(mChannelId, latest = mOldestMessageTs)
                else -> IMRepository.history(mChannelId, latest = mOldestMessageTs)
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mView.showLoadingMore(false)

                        if (it.ok && it.messages != null) {
                            if (it.messages.isNotEmpty()) {
                                mView.showDataOfNextPage(generateEpoxyModels(it.messages))

                                mOldestMessageTs = it.messages.last().ts
                                mLatestMessageTs = it.messages.first().ts
                            }
                        }

                        mHasMore = it.hasMore ?: false
                    }, {
                        it.printStackTrace()
                        mView.showLoadingMore(false)
                    })
            mCompositeDisposable.add(disposable)
        }
    }

    override fun generateEpoxyModels(dataList: List<*>): Collection<EpoxyModel<*>> {
        val list = dataList.filter { it is Message }
                .map { message ->
                    MessageModel_()
                            .message(message as Message)
                            .itemOnClickListener { _, _, _, _ ->
                                if (message.subtype == null) {
                                    mView.gotoMessageDetails(message)
                                }
                            }
                            .itemOnLongClickListener { _, _, _, _ ->
                                if (message.subtype == null) {
                                    mView.showMessageActions(message, mChannelId)
                                }
                                message.subtype == null
                            }
                }

        mEpoxyModels.addAll(list)

        return list
    }

    override fun viewDetails() {
        mChannel?.let {
            mView.gotoChannelDetails(it)
        }
    }

    override fun sendMessage(content: String) {
        val disposable = ChatRepository.postMessage(mChannelId, content, true)
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

    override fun updateMessage(content: String, message: Message) {
        val disposable = ChatRepository.update(mChannelId, content, message.ts, true, message.attachments.toString(), false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
                        mView.dismissMessageAction()
                    }
                }, {
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun handleMessageEvent(intent: Intent) {
        val messageEvent = intent.getParcelableExtra<io.github.tonnyl.latticify.data.event.Message>(Constants.BROADCAST_EXTRA)

        if (messageEvent.hidden == true) {
            if (messageEvent.subtype == "message_deleted" && !messageEvent.deletedTs.isNullOrEmpty()) { // Message was deleted.
                mEpoxyModels.firstOrNull {
                    it is MessageModel_
                            && it.message.ts == messageEvent.previousMessage?.ts
                }?.let {
                    mView.deleteMessage(it)
                    mEpoxyModels.remove(it)
                }
            } else if (messageEvent.subtype == "message_changed") { // // Message was changed.
                mEpoxyModels.firstOrNull {
                    it is MessageModel_
                            && it.message.ts == messageEvent.previousMessage?.ts
                }?.let {
                    with(it as MessageModel_) {
                        message.edited = messageEvent.message?.edited
                        message.text = messageEvent.message?.text
                        message.ts = messageEvent.previousMessage?.ts ?: messageEvent.edited?.ts ?: messageEvent.ts
                    }
                    mView.updateMessage(it, it.message)
                }
            }
        } else {
            getLatestMessage()

            mLatestMessageTs = messageEvent.ts
        }

    }

    override fun starMessage(timestamp: String, starred: Boolean) {
        val disposable = (if (starred) StarredItemsRepository.add(mChannelId, "", "", timestamp = timestamp) else StarredItemsRepository.remove(mChannelId, "", "", timestamp))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
                        mView.showMessageStarred(starred)

                        mEpoxyModels.firstOrNull {
                            it is MessageModel_ && it.message.ts == timestamp
                        }?.let {
                            (it as MessageModel_).message.isStarred = starred
                        }
                    }
                }, {
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun copyLinkToMessage(messageTimestamp: String) {
        val disposable = ChatRepository.getPermalink(mChannelId, messageTimestamp)
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
        val disposable = ChatRepository.delete(mChannelId, messageTimestamp, true)
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
        val disposable = (if (toPin) PinsRepository().add(mChannelId, "", "", timestamp) else PinsRepository().remove(mChannelId, "", "", timestamp))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {

                    }
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

    private fun getLatestMessage() {
        val disposable = (when {
            mChannel?.isGroup == true -> GroupsRepository.history(mChannelId, oldest = mLatestMessageTs, count = 1)
            mChannel?.isChannel == true -> ChannelsRepository.history(mChannelId, oldest = mLatestMessageTs, count = 1)
            else -> IMRepository.history(mChannelId, oldest = mLatestMessageTs, count = 1)
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok && it.messages != null) {
                        if (it.messages.isNotEmpty()) {
                            mView.insertNewMessage(generateEpoxyModels(it.messages).first(), 0)
                        }
                    }
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

}