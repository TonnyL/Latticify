package io.github.tonnyl.latticify.ui.chat

import android.content.Intent
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.ChannelWrapper
import io.github.tonnyl.latticify.data.GroupWrapper
import io.github.tonnyl.latticify.data.Message
import io.github.tonnyl.latticify.data.repository.*
import io.github.tonnyl.latticify.epoxy.MessageModel_
import io.github.tonnyl.latticify.ui.profile.ProfileActivity
import io.github.tonnyl.latticify.ui.profile.ProfilePresenter
import io.github.tonnyl.latticify.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by lizhaotailang on 06/10/2017.
 *
 */
class ChatPresenter(

        private val mView: ChatContract.View,
        private val mChannelId: String,
        private val mIsIM: Boolean

) : ChatContract.Presenter {

    override var mOldestMessageTs: String = ""
    override var mLatestMessageTs: String = ""
    override var mHasMore: Boolean = false

    private val mCompositeDisposable = CompositeDisposable()
    private var mChannel: Channel? = null

    private val mEpoxyModels = mutableListOf<EpoxyModel<*>>()

    companion object {
        val KEY_EXTRA_CHANNEL = "KEY_EXTRA_CHANNEL"
        val KEY_EXTRA_CHANNEL_ID = "KEY_EXTRA_CHANNEL_ID"
        val KEY_EXTRA_IS_IM = "KEY_EXTRA_IS_IM"
    }

    init {
        mView.setPresenter(this)
    }

    constructor(view: ChatContract.View, channel: Channel, isIM: Boolean) : this(view, channel.id, isIM) {
        mChannel = channel
    }

    override fun subscribe() {
        mView.setLoadingIndicator(true)

        fetchData()

        mChannel?.let {
            if (it.isChannel == true || it.isGroup == true) {
                mView.showChannel(it)
            } else {
                mChannel?.user?.let {
                    val disposable = UserPoolRepository.getUser(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ user ->
                                with(user.profile.displayName) {
                                    if (this.trim().isNotEmpty()) {
                                        mView.showUsername(this)
                                    } else {
                                        mView.showUsername(user.realName)
                                    }
                                }
                            }, {

                            })
                    mCompositeDisposable.add(disposable)
                }
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
                            .itemOnClickListener { _, _, clickedView, _ ->
                                if (clickedView is ImageView) {
                                    message.user?.let {
                                        val disposable = UserPoolRepository.getUser(it)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe({
                                                    clickedView.context.startActivity(Intent(clickedView.context, ProfileActivity::class.java).apply {
                                                        putExtra(ProfilePresenter.KEY_EXTRA_USER, it)
                                                    })
                                                }, {

                                                })
                                        mCompositeDisposable.add(disposable)
                                    }
                                } else if (message.subtype == null || message.subtype == "bot_message") {
                                    mView.showMessageActions(message, mChannelId)
                                } else if (message.subtype == "file_share") {
                                    message.file?.let {
                                        mView.gotoFileDetails(it.id)
                                    }
                                }
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

    override fun closeIM() {
        val disposable = IMRepository.close(mChannelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
                        mView.finishActivity()
                    }
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

    override fun uploadFile(path: String) {
        val file = File(path)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val fileName = RequestBody.create(MediaType.parse("text/plain"), file.name)
        val title = RequestBody.create(MediaType.parse("text/plain"), file.name)
        val channelIds = RequestBody.create(MediaType.parse("text/plain"), mChannelId)
        val fileType = RequestBody.create(MediaType.parse("text/plain"), "auto")
        val comment = RequestBody.create(MediaType.parse("text/plain"), "")

        val disposable = FilesRepository().upload(channelIds, null, body, fileName, fileType, comment, title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {

                    } else {

                    }
                }, {
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun inviteMember() {
        val disposable = ConversationsRepository.members(mChannelId)
                .subscribeOn(Schedulers.io())
                .map {
                    if (it.ok) {
                        it.members
                    } else {
                        Collections.emptyList<String>()
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mView.gotoInviteMember(ArrayList(it))
                }, {
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun updateChannel() {
        val disposable = ChannelsRepository.info(mChannelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.ok) {
                        mChannel = it.channel
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