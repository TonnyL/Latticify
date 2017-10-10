package io.github.tonnyl.latticify.ui.channel

import android.util.Log
import android.view.Menu
import android.view.View
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.Message
import io.github.tonnyl.latticify.data.repository.ChannelsRepository
import io.github.tonnyl.latticify.data.repository.GroupsRepository
import io.github.tonnyl.latticify.data.repository.IMRepository
import io.github.tonnyl.latticify.data.repository.RtmRepository
import io.github.tonnyl.latticify.epoxy.MessageModel_
import io.github.tonnyl.latticify.ui.message.MessageActivity
import io.github.tonnyl.latticify.ui.message.MessagePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import org.jetbrains.anko.startActivity

/**
 * Created by lizhaotailang on 06/10/2017.
 *
 */
class ChannelPresenter(view: ChannelContract.View, channel: Channel) : ChannelContract.Presenter {

    override var mOldestMessageTs: String = ""
    override var mHasMore: Boolean = false

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mView = view
    private var mChannelId = channel.id
    private val mChannel = channel
    private var mWebSocketUrl = ""
    private val mOkHttpClient = OkHttpClient()
    private var mRequest: Request? = null
    private var mWebSocket: WebSocket? = null

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

    override fun subscribe() {
        mView.setLoadingIndicator(true)
        fetchData()

        fetchRtm()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun fetchData() {
        val disposable = (when {
            mChannel.isPrivate == true -> GroupsRepository.history(mChannelId)
            mChannel.isChannel == true -> ChannelsRepository.history(mChannelId)
            else -> IMRepository.history(mChannelId)
        }).subscribeOn(Schedulers.io())
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
                mChannel.isPrivate == true -> GroupsRepository.history(mChannelId, oldest = mOldestMessageTs)
                mChannel.isChannel == true -> ChannelsRepository.history(mChannelId, oldest = mOldestMessageTs)
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
                                    it.context.startActivity<MessageActivity>(MessagePresenter.KEY_EXTRA_MESSAGE to message)
                                })
                                .itemOnCreateContextMenuListener({ menu, _, _ ->
                                    menu.add(Menu.NONE, R.id.action_copy_text, 0, "Copy text")
                                    menu.add(Menu.NONE, R.id.action_share, 0, "Share")
                                })
                                .directMessage(mChannel.isIm ?: false)
                    }

    override fun fetchRtm() {
        val disposable = RtmRepository.connect(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mWebSocketUrl = it.url
                    Log.d("url", "" + it)
                    /*mRequest = Request.Builder().url(mWebSocketUrl).build()
                    mWebSocket = mOkHttpClient.newWebSocket(mRequest, object : WebSocketListener() {
                        override fun onOpen(webSocket: WebSocket?, response: Response?) {
                            super.onOpen(webSocket, response)
                            Log.d("onOpen", "response -> $response")
                        }

                        override fun onMessage(webSocket: WebSocket?, text: String?) {
                            super.onMessage(webSocket, text)
                            Log.d("onMessage", "text -> $text")
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

                    })*/
                }, {
                    it.printStackTrace()
                    Log.d("error", "" + it.message)
                })
        mCompositeDisposable.add(disposable)
    }

    override fun connectWebSocket() {
    }

    override fun disconnectWebSocket() {

    }

}