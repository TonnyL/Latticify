package io.github.tonnyl.latticify.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.google.gson.Gson
import io.github.tonnyl.latticify.data.RtmReceivedMessage
import io.github.tonnyl.latticify.data.event.Hello
import io.github.tonnyl.latticify.data.event.Message
import io.github.tonnyl.latticify.data.event.UserTyping
import io.github.tonnyl.latticify.data.repository.RtmRepository
import io.github.tonnyl.latticify.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okio.ByteString

class WebSocketService : Service() {

    private val mCompositeDisposable = CompositeDisposable()
    private var mWebSocket: WebSocket? = null
    private lateinit var mLocalBroadcastManager: LocalBroadcastManager

    private val mGson = Gson()

    val TAG = "WebSocketService"

    override fun onCreate() {
        super.onCreate()

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this@WebSocketService)

        Log.d(TAG, "onCreate")

        connectWebSocket()
    }

    override fun onDestroy() {
        super.onDestroy()

        mCompositeDisposable.clear()

        mWebSocket?.cancel()

        Log.d(TAG, "onDestroy")
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind")

        return null
    }

    private fun connectWebSocket() {
        Log.d(TAG, "connectWebSocket")

        val tag = "WEB_SOCKET"

        val disposable = RtmRepository.connect(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
                        mWebSocket = OkHttpClient().newWebSocket(Request.Builder().url(it.url).build(), object : WebSocketListener() {
                            override fun onOpen(webSocket: WebSocket?, response: Response?) {
                                super.onOpen(webSocket, response)
                                Log.d(tag, "onOpen response -> $response")
                            }

                            override fun onMessage(webSocket: WebSocket?, text: String?) {
                                super.onMessage(webSocket, text)
                                Log.d(tag, "onMessage text -> $text")
                                text?.let {
                                    val msg = mGson.fromJson<RtmReceivedMessage>(it, RtmReceivedMessage::class.java)
                                    var intent = Intent("")
                                    when (msg.type) {
                                        "user_typing" -> {
                                            intent = Intent(Constants.FILTER_USER_TYPING)
                                            intent.putExtra(Constants.BROADCAST_EXTRA, mGson.fromJson(it, UserTyping::class.java))
                                        }
                                        "message" -> {
                                            intent = Intent(Constants.FILTER_MESSAGE)
                                            intent.putExtra(Constants.BROADCAST_EXTRA, mGson.fromJson(it, Message::class.java))
                                        }
                                        "channel_marked" -> {

                                        }
                                        "pref_change" -> {

                                        }
                                        "member_joined_channel" -> {

                                        }
                                        "desktop_notification" -> {

                                        }
                                        "im_created" -> {

                                        }
                                        "im_open" -> {

                                        }
                                        "file_public" -> {

                                        }
                                        "file_shared" -> {

                                        }
                                        "hello" -> {
                                            intent = Intent(Constants.FILTER_HELLO)
                                            intent.putExtra(Constants.BROADCAST_EXTRA, mGson.fromJson(it, Hello::class.java))
                                        }
                                        "goodbye" -> {

                                        }
                                        else -> {

                                        }
                                    }
                                    intent.let {
                                        mLocalBroadcastManager.sendBroadcast(it)
                                    }

                                }
                            }

                            override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
                                super.onMessage(webSocket, bytes)
                                Log.d(tag, "onMessage bytes -> $bytes")
                            }

                            override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
                                super.onClosed(webSocket, code, reason)
                                Log.d(tag, "onClosed code -> $code reason -> $reason")
                            }

                            override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
                                super.onFailure(webSocket, t, response)
                                Log.d(tag, "onFailure throwable -> ${t?.message} response -> $response")
                            }
                        })
                    }
                }, {
                    it.printStackTrace()
                    Log.d(tag, "error $it")
                })
        mCompositeDisposable.add(disposable)
    }

}