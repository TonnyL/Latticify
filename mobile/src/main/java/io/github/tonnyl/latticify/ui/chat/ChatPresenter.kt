package io.github.tonnyl.latticify.ui.chat

import android.util.Log
import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.google.gson.Gson
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.*
import io.github.tonnyl.latticify.data.repository.ChannelsRepository
import io.github.tonnyl.latticify.data.repository.GroupsRepository
import io.github.tonnyl.latticify.data.repository.IMRepository
import io.github.tonnyl.latticify.data.repository.RtmRepository
import io.github.tonnyl.latticify.epoxy.MessageModel_
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okio.ByteString

/**
 * Created by lizhaotailang on 06/10/2017.
 *
 */
class ChatPresenter(view: ChatContract.View, channelId: String) : ChatContract.Presenter {

    override var mOldestMessageTs: String = ""
    override var mHasMore: Boolean = false

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mView = view
    private var mChannelId = channelId
    private var mChannel: Channel? = null
    private var mWebSocket: WebSocket? = null
    private var mRtmResponseMessage: RtmResponseMessageWrapper? = null
    private val mGson = Gson()

    companion object {
        @JvmField
        val KEY_EXTRA_CHANNEL = "KEY_EXTRA_CHANNEL"
        @JvmField
        val KEY_EXTRA_CHANNEL_ID = "KEY_EXTRA_CHANNEL_ID"
    }

    init {
        mView.setPresenter(this)
        mChannelId = mChannelId
    }

    constructor(view: ChatContract.View, channel: Channel) : this(view, channel.id) {
        mChannel = channel
    }

    override fun subscribe() {
        mView.setLoadingIndicator(true)

        fetchData()

//        connectWebSocket()

        mChannel?.let { if (it.isChannel == true) mView.showChannel(it) }
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()

//        mWebSocket?.cancel()
    }

    override fun fetchData() {
        val disposable = ChannelsRepository.info(mChannelId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap {
                    mChannel = it.channel
                    (when {
                        mChannel?.isPrivate == true -> GroupsRepository.history(mChannelId)
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
                mChannel?.isPrivate == true -> GroupsRepository.history(mChannelId, latest = mOldestMessageTs)
                mChannel?.isChannel == true -> ChannelsRepository.history(mChannelId, latest = mOldestMessageTs)
                else -> IMRepository.history(mChannelId, oldest = mOldestMessageTs)
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mView.showLoadingMore(false)

                        if (it.ok && it.messages != null) {
                            if (it.messages.isNotEmpty()) {
                                mView.showDataOfNextPage(generateEpoxyModels(it.messages))

                                mOldestMessageTs = it.messages.last().ts
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

    override fun generateEpoxyModels(dataList: List<*>): Collection<EpoxyModel<*>> =
            dataList.filter { it is Message }
                    .map { message ->
                        MessageModel_()
                                .message(message as Message)
                                .itemOnClickListener(View.OnClickListener {
                                    mView.gotoMessageDetails(message)
                                })
                                .itemOnCreateContextMenuListener({ menu, _, _ ->
                                    menu.add(R.id.group_message_0, R.id.action_copy_text, 0, R.string.copy_text)
                                    menu.add(R.id.group_message_0, R.id.action_copy_link_to_message, 0, R.string.copy_link_to_message)
                                    menu.add(R.id.group_message_0, R.id.action_share_message, 0, R.string.share_message)

                                    menu.add(R.id.group_message_1, R.id.action_add_reaction, 0, R.string.add_reaction)

                                    menu.add(R.id.group_message_2, R.id.action_star, 0, R.string.star)
                                    menu.add(R.id.group_message_2, R.id.action_remind_me, 0, R.string.remind_me)
                                    menu.add(R.id.group_message_2, R.id.action_pin_to_conversation, 0, R.string.pin_to_conversation)

                                    menu.add(R.id.group_message_3, R.id.action_edit_message, 0, R.string.edit_message)
                                    menu.add(R.id.group_message_3, R.id.action_delete, 0, R.string.delete)
                                })
                                .directMessage(mChannel?.isIm ?: false)
                    }

    override fun viewDetails() {
        mChannel?.let {
            mView.gotoChannelDetails(it)
        }
    }

    override fun connectWebSocket() {
        val disposable = RtmRepository.connect(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mRtmResponseMessage = it
                    mWebSocket = OkHttpClient().newWebSocket(Request.Builder().url(it.url).build(), object : WebSocketListener() {
                        override fun onOpen(webSocket: WebSocket?, response: Response?) {
                            super.onOpen(webSocket, response)
                            Log.d("onOpen", "response -> $response")
                        }

                        override fun onMessage(webSocket: WebSocket?, text: String?) {
                            super.onMessage(webSocket, text)
                            Log.d("onMessage", "text -> $text")
                            text?.let {
                                val msg = mGson.fromJson<RtmReceivedMessage>(it, RtmReceivedMessage::class.java)
                                Log.d("onMessage", "message -> $text")
                                if (msg.type == "typing") {

                                }
                            }
                        }

                        override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
                            super.onMessage(webSocket, bytes)
                            Log.d("onMessage", "bytes -> $bytes")
                        }

                        override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
                            super.onClosed(webSocket, code, reason)
                            Log.d("onClosed", "code -> $code reason -> $reason")
                        }

                        override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
                            super.onFailure(webSocket, t, response)
                            Log.d("onFailure", "throwable -> ${t?.message} response -> $response")
                        }

                    })
                }, {
                    it.printStackTrace()
                    Log.d("error", "" + it.message)
                })
        mCompositeDisposable.add(disposable)
    }

    override fun sendMessage(content: String) {
        mRtmResponseMessage?.let {
            mWebSocket?.send(mGson.toJson(RtmSendingMessage(it.rtmSelf.id, "message", mChannelId, content)))
        }
    }

    private fun getLatestOneMessage() {
        val disposable = (when {
            mChannel?.isPrivate == true -> GroupsRepository.history(mChannelId, oldest = mOldestMessageTs, count = 1)
            mChannel?.isChannel == true -> ChannelsRepository.history(mChannelId, oldest = mOldestMessageTs, count = 1)
            else -> IMRepository.history(mChannelId, oldest = mOldestMessageTs, count = 1)
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {

                })
        mCompositeDisposable.add(disposable)
    }

}