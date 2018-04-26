package io.github.tonnyl.latticify.service

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.google.gson.Gson
import io.github.tonnyl.latticify.R
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
import java.util.*

class WebSocketService : Service() {

    private val mCompositeDisposable = CompositeDisposable()
    private var mWebSocket: WebSocket? = null
    private lateinit var mLocalBroadcastManager: LocalBroadcastManager

    private val mGson = Gson()

    private val mDownloadReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == intent.action) {
                val query = DownloadManager.Query()
                val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                query.setFilterById(id)
                val c = manager.query(query)
                if (c.moveToFirst()) {
                    val columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS)
                    if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                        val name = c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE))
                        pushNotification(name)

                        Log.d(TAG, "A file download completed. File name $name")
                    }
                }
            }
        }

    }

    val TAG = "WebSocketService"

    override fun onCreate() {
        super.onCreate()

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this@WebSocketService)

        registerReceiver(mDownloadReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        Log.d(TAG, "onCreate")

        connectWebSocket()
    }

    override fun onDestroy() {
        super.onDestroy()

        mCompositeDisposable.clear()

        mWebSocket?.cancel()

        unregisterReceiver(mDownloadReceiver)

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

    // Push a notification when the download job completed.
    private fun pushNotification(title: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelID = "CHANNEL_ID_01"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_download)
            val desc = getString(R.string.notification_channel_download_desc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, importance)
            channel.description = desc
            channel.enableVibration(true)
            channel.enableLights(true)
            channel.lightColor = Color.GREEN
            channel.setShowBadge(false)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, channelID)
        builder.setContentTitle(title)
        builder.setContentText(getString(R.string.download_complete))
        builder.priority = NotificationCompat.PRIORITY_MAX
        builder.setSmallIcon(R.drawable.ic_app_notification)
        builder.setContentIntent(PendingIntent.getActivity(applicationContext, 0, Intent(DownloadManager.ACTION_VIEW_DOWNLOADS), 0))
        builder.setWhen(System.currentTimeMillis())
        builder.setAutoCancel(true)
        val n = builder.build()
        notificationManager.notify(Random().nextInt() + 1, n)
    }

}