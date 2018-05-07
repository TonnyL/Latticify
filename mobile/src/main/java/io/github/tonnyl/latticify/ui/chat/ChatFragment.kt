package io.github.tonnyl.latticify.ui.chat

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.TextView
import android.widget.Toast
import com.airbnb.epoxy.EpoxyModel
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import io.github.tonnyl.charles.Charles
import io.github.tonnyl.charles.utils.PathUtils
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.Message
import io.github.tonnyl.latticify.data.event.UserTyping
import io.github.tonnyl.latticify.data.repository.UserPoolRepository
import io.github.tonnyl.latticify.epoxy.LoadingModel_
import io.github.tonnyl.latticify.glide.CharlesGlideV4Engine
import io.github.tonnyl.latticify.glide.MatisseGlideV4Engine
import io.github.tonnyl.latticify.ui.channel.invite.InviteMemberActivity
import io.github.tonnyl.latticify.ui.channel.invite.InviteMemberPresenter
import io.github.tonnyl.latticify.ui.channel.profile.ChannelProfileActivity
import io.github.tonnyl.latticify.ui.channel.profile.ChannelProfilePresenter
import io.github.tonnyl.latticify.ui.file.FileActivity
import io.github.tonnyl.latticify.ui.file.FilePresenter
import io.github.tonnyl.latticify.util.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.layout_input.*
import kotlinx.android.synthetic.main.layout_message_action.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by lizhaotailang on 06/10/2017.
 *
 */
class ChatFragment : Fragment(), ChatContract.View {

    override var ignoreScrollChange: Boolean = false

    private lateinit var mPresenter: ChatContract.Presenter

    private var mIsLoading = false
    private val mAdapter = ChatMessageAdapter()
    private val mLoadMoreModel = LoadingModel_()

    private val mCompositeDisposable = CompositeDisposable()

    private var mSubTitle = ""

    private var mIsEditingMessage = false
    private var mEditingMessage: Message? = null

    private var mChannel: Channel? = null
    private var mIsIM: Boolean? = false

    private var imageFilePath: String? = null

    private val mUserTypingReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                val userTyping = it.getParcelableExtra<UserTyping>(Constants.BROADCAST_EXTRA)

                activity?.let {
                    val disposable = UserPoolRepository.getUser(userTyping.user)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ user ->
                                mSubTitle = context?.resources?.getQuantityString(R.plurals.user_typing, 1, if (user.profile.displayName.isNotEmpty()) user.profile.displayName else user.name) ?: ""
                                (it as ChatActivity).supportActionBar?.subtitle = mSubTitle
                            }, {

                            })
                    mCompositeDisposable.add(disposable)

                }
                val disposable = Observable.timer(2000L, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            activity?.let {
                                (it as ChatActivity).supportActionBar?.subtitle = ""
                            }
                        }, {

                        })
                mCompositeDisposable.add(disposable)
            }
        }

    }

    private val mMessageReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                mPresenter.handleMessageEvent(it)
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(): ChatFragment = ChatFragment()

        val REQUEST_CHOOSE_IMAGE = 101
        val REQUEST_CHOOSE_FILE = 102
        val REQUEST_TAKE_PHOTO = 103

        val REQUEST_CHANNEL_DETAILS = 110
        val REQUEST_INVITE_MEMBER = 111
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_chat, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout.isEnabled = false

        mIsIM = activity?.intent?.getBooleanExtra(ChatPresenter.KEY_EXTRA_IS_IM, false)

        context?.let {
            swipeRefreshLayout.setColorSchemeColors(it.getColor(R.color.colorAccent))
        }
        swipeRefreshLayout.setOnRefreshListener {
            mPresenter.fetchData()
        }

        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
            adapter = mAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!ignoreScrollChange) {
                        val totalItemCount = recyclerView?.adapter?.itemCount
                        val lastVisibleItemPosition = recyclerView?.layoutManager?.let {
                            (it as LinearLayoutManager).findLastVisibleItemPosition()
                        }

                        if (!mIsLoading
                                && totalItemCount != null
                                && lastVisibleItemPosition != null
                                && lastVisibleItemPosition == totalItemCount - 1) {
                            mIsLoading = true
                            mPresenter.fetchDataOfNextPage()
                        }
                    }
                }
            })
        }

        messageEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(editable: Editable?) {
                if (editable.isNullOrBlank()) {
                    sendMessageImageView.clearColorFilter()
                } else {
                    context?.let {
                        sendMessageImageView.setColorFilter(it.getColor(R.color.colorPrimary))
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        sendMessageImageView.setOnClickListener {
            if (!messageEditText.text.isNullOrBlank()) {
                if (mIsEditingMessage && mEditingMessage != null) {
                    mEditingMessage?.let {
                        mPresenter.updateMessage(messageEditText.text.toString(), it)
                    }
                } else {
                    mPresenter.sendMessage(messageEditText.text.toString())
                    messageEditText.setText("")
                }
            }
        }

        plusImageView.setOnClickListener {
            showBottomSheetDialog()
        }

        setHasOptionsMenu(true)

        mPresenter.subscribe()

        context?.let {
            LocalBroadcastManager.getInstance(it).registerReceiver(mUserTypingReceiver, IntentFilter(Constants.FILTER_USER_TYPING))
            LocalBroadcastManager.getInstance(it).registerReceiver(mMessageReceiver, IntentFilter(Constants.FILTER_MESSAGE))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mPresenter.unsubscribe()
        mCompositeDisposable.clear()

        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(mUserTypingReceiver)
            LocalBroadcastManager.getInstance(it).unregisterReceiver(mMessageReceiver)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.menu_channel, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)

        listOf(menu?.getItem(0), menu?.getItem(1)).forEach {
            it?.isVisible = mIsIM != true
        }
        listOf(menu?.getItem(2), menu?.getItem(3)).forEach {
            it?.isVisible = mIsIM == true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            android.R.id.home -> {
                activity?.onBackPressed()
            }

        // Channel
            R.id.action_view_details -> {
                mPresenter.viewDetails()
            }
            R.id.action_invite_members_to_channel -> {
                mPresenter.inviteMember()
            }

        // IM
            R.id.action_view_user_profile -> {

            }
            R.id.action_close_im -> {
                mPresenter.closeIM()
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_TAKE_PHOTO -> {
                    val uri = Uri.parse(imageFilePath)
                    imageFilePath?.let {
                        mPresenter.uploadFile(it)
                    }
                }
                REQUEST_CHOOSE_IMAGE -> {
                    val paths = Matisse.obtainResult(data)
                    paths.firstOrNull()?.let { uri ->
                        context?.let {
                            PathUtils.getPath(it, uri)?.let {
                                mPresenter.uploadFile(it)
                            }
                        }
                    }
                }
                REQUEST_CHOOSE_FILE -> {
                    val paths = Charles.obtainPathResult(data)
                    paths?.firstOrNull()?.let {
                        mPresenter.uploadFile(it)
                    }
                }
                REQUEST_CHANNEL_DETAILS -> {
                    val exitBecauseLeftChannel = data?.getBooleanExtra(ChannelProfileActivity.EXTRA_RESULT_LEAVE, false)
                    val exitBecauseChannelArchived = data?.getBooleanExtra(ChannelProfileActivity.EXTRA_RESULT_ARCHIVE, false)
                    if (exitBecauseChannelArchived == true || exitBecauseLeftChannel == true) {
                        activity?.finish()
                    }
                }
                REQUEST_INVITE_MEMBER -> {
                    if (data?.getBooleanExtra(InviteMemberActivity.EXTRA_RESULT_INVITE_MEMBER, false) == true) {
                        mPresenter.updateChannel()
                    }
                }
            }
        }
    }

    override fun setPresenter(presenter: ChatContract.Presenter) {
        mPresenter = presenter
    }

    override fun showData(epoxyModels: Collection<EpoxyModel<*>>) {
        recyclerView.visibility = View.VISIBLE
        emptyView.visibility = View.GONE
        errorView.visibility = View.GONE
        mAdapter.removeAllModels()
        mAdapter.addModels(epoxyModels)
    }

    override fun showDataOfNextPage(epoxyModels: Collection<EpoxyModel<*>>) {
        mAdapter.addModels(epoxyModels)
    }

    override fun addModel(epoxyModel: EpoxyModel<*>) {
        mAdapter.addModel(epoxyModel)
    }

    override fun setLoadingIndicator(loading: Boolean) {
        swipeRefreshLayout.isRefreshing = loading
        emptyView.visibility = View.GONE
        errorView.visibility = View.GONE

        mIsLoading = loading
    }

    override fun showLoadingMore(loadingMore: Boolean) {
        if (loadingMore) {
            addModel(mLoadMoreModel)
        } else {
            mAdapter.removeModel(mLoadMoreModel)
        }

        mIsLoading = loadingMore
    }

    override fun showEmptyView() {
        emptyView.visibility = View.VISIBLE
    }

    override fun showErrorView() {
        errorView.visibility = View.VISIBLE
    }

    override fun showChannel(channel: Channel) {
        with(activity as ChatActivity) {
            supportActionBar?.title = channel.name
        }

        mChannel = channel
    }

    override fun showUsername(username: String) {
        with(activity as ChatActivity) {
            supportActionBar?.title = username
        }
    }

    override fun gotoChannelDetails(channel: Channel) {
        val intent = Intent(activity, ChannelProfileActivity::class.java).apply {
            putExtra(ChannelProfilePresenter.KEY_EXTRA_CHANNEL, channel)
        }
        startActivityForResult(intent, REQUEST_CHANNEL_DETAILS)
    }

    override fun insertNewMessage(epoxyModel: EpoxyModel<*>, position: Int) {
        mAdapter.insertModel(epoxyModel, position)
    }

    override fun deleteMessage(epoxyModel: EpoxyModel<*>) {
        mAdapter.removeModel(epoxyModel)
    }

    override fun updateMessage(epoxyModel: EpoxyModel<*>, message: Message) {
        mAdapter.updateModel(epoxyModel, message)
    }

    @SuppressLint("InflateParams")
    override fun showMessageActions(message: Message, channelId: String) {
        context?.let {
            val dialog = BottomSheetDialog(it)
            val view = layoutInflater.inflate(R.layout.layout_message_actions, null)
            dialog.setContentView(view)

            view.findViewById<TextView>(R.id.actionCopyText).setOnClickListener {
                dialog.dismiss()

                context?.let { context ->
                    (message.text
                            ?: message.attachments?.getOrNull(0)?.let { "${it.title}\n${it.text}" })?.let {
                        val manager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clipData = ClipData.newPlainText("text", it)
                        manager.primaryClip = clipData

                        Toast.makeText(context, R.string.copied, Toast.LENGTH_SHORT).show()
                    } ?: run {
                        Toast.makeText(context, R.string.copy_failed, Toast.LENGTH_SHORT).show()
                    }
                }

            }

            view.findViewById<TextView>(R.id.actionCopyLinkToMessage).setOnClickListener {
                dialog.dismiss()

                mPresenter.copyLinkToMessage(message.ts)
            }

            view.findViewById<TextView>(R.id.actionShareMessage).setOnClickListener {
                dialog.dismiss()

                messageActionLayout.visibility = View.VISIBLE
                actionImageView.setImageResource(R.drawable.ic_forward_black_24dp)
                userNameTextView.text = message.user
                messageContentTextView.text = message.text
                dismissImageView.setOnClickListener {
                    messageActionLayout.visibility = View.GONE
                }
            }

            view.findViewById<TextView>(R.id.actionStar).apply {
                setText(if (message.isStarred != true) R.string.star else R.string.unstar)
                setCompoundDrawablesWithIntrinsicBounds(context?.getDrawable(if (message.isStarred != true) R.drawable.ic_star_border_black_24dp else R.drawable.ic_star_black_24dp), null, null, null)

                setOnClickListener {
                    dialog.dismiss()

                    mPresenter.starMessage(message.ts, message.isStarred != true)
                }
            }

            val pinTextView = view.findViewById<TextView>(R.id.actionPinToConversation)
            pinTextView.text = getString(if (message.pinnedTo?.contains(channelId) == true) R.string.unpin_to_conversation else R.string.pin_to_conversation)
            pinTextView.setOnClickListener {
                dialog.dismiss()

                mPresenter.pinMessage(message.ts, message.pinnedTo?.contains(message.ts) != true)
            }

            view.findViewById<TextView>(R.id.actionEditMessage).setOnClickListener {
                mIsEditingMessage = true
                mEditingMessage = message

                dismissImageView.setOnClickListener {
                    dismissMessageAction()
                }

                dialog.setOnDismissListener {
                    actionImageView.setImageResource(R.drawable.ic_edit_black_24dp)
                    userNameTextView.text = message.user
                    messageContentTextView.text = message.text
                    messageEditText.setText(message.text)
                    // Move the cursor to end
                    messageEditText.setSelection(messageEditText.text.length)
                    sendMessageImageView.setImageResource(R.drawable.ic_done_black_24dp)
                    messageActionLayout.visibility = View.VISIBLE
                }

                dialog.dismiss()
            }

            view.findViewById<TextView>(R.id.actionDeleteMessage).setOnClickListener {
                dialog.dismiss()

                mPresenter.deleteMessage(message.ts)
            }

            dialog.show()
        }
    }

    override fun dismissMessageAction() {
        messageEditText.setText("")
        sendMessageImageView.setImageResource(R.drawable.ic_send_black_24dp)
        messageActionLayout.visibility = View.GONE

        mIsEditingMessage = false
        mEditingMessage = null
    }

    override fun showMessageStarred(starred: Boolean) {
        Toast.makeText(context, if (starred) R.string.msg_starred else R.string.msg_unstarred, Toast.LENGTH_SHORT).show()
    }

    override fun copyLink(url: String) {
        context?.let {
            val manager = it.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", url)
            manager.primaryClip = clipData

            Toast.makeText(context, R.string.copied, Toast.LENGTH_SHORT).show()
        }
    }

    override fun displayMessage(content: String) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
    }

    override fun showMessagePinned(pinned: Boolean) {
        Toast.makeText(context, if (pinned) R.string.pinned else R.string.unpinned, Toast.LENGTH_SHORT).show()
    }

    override fun finishActivity() {
        activity?.onBackPressed()
    }

    override fun gotoFileDetails(fileId: String) {
        startActivity(Intent(context, FileActivity::class.java).apply {
            putExtra(FilePresenter.KEY_EXTRA_FILE_ID, fileId)
        })
    }

    override fun gotoInviteMember(list: ArrayList<String>) {
        startActivityForResult(Intent(context, InviteMemberActivity::class.java).apply {
            putExtra(InviteMemberPresenter.KEY_EXTRA_CHANNEL_ID, mChannel?.id)
        }, REQUEST_INVITE_MEMBER)
    }

    @SuppressLint("InflateParams")
    private fun showBottomSheetDialog() {
        context?.let {
            val dialog = BottomSheetDialog(it)
            val view = layoutInflater.inflate(R.layout.layout_chat_bottom_sheet, null)
            dialog.setContentView(view)

            view.findViewById<TextView>(R.id.actionCamera).setOnClickListener {
                dialog.dismiss()

                activity?.let {
                    RxPermissions(it).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe({
                                openCameraIntent()
                            }, {

                            }, {

                            }, {

                            })

                }

            }

            view.findViewById<TextView>(R.id.actionGallery).setOnClickListener {
                dialog.dismiss()

                activity?.let {
                    RxPermissions(it).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe({
                                Matisse.from(this)
                                        .choose(MimeType.allOf())
                                        .imageEngine(MatisseGlideV4Engine())
                                        .countable(true)
                                        .maxSelectable(1)
                                        .theme(R.style.Latticify_MatisseStyle)
                                        .forResult(REQUEST_CHOOSE_IMAGE)
                            }, {

                            }, {

                            }, {

                            })

                }

            }

            view.findViewById<TextView>(R.id.actionFile).setOnClickListener {
                dialog.dismiss()

                activity?.let {
                    RxPermissions(it).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe({
                                Charles.from(this)
                                        .choose()
                                        .imageEngine(CharlesGlideV4Engine())
                                        .progressRate(true)
                                        .maxSelectable(1)
                                        .theme(R.style.Latticify_CharlesStyle)
                                        .forResult(REQUEST_CHOOSE_FILE)
                            }, {

                            }, {

                            }, {

                            })

                }

            }

            dialog.show()
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
        imageFilePath = image.absolutePath
        return image
    }

    private fun openCameraIntent() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (cameraIntent.resolveActivity(activity?.packageManager) != null) {
            val photoFile: File?
            try {
                photoFile = createImageFile()
            } catch (e: Exception) {
                e.printStackTrace()
                return
            }

            context?.let {
                val photoURI = FileProvider.getUriForFile(it, "${it.packageName}.provider", photoFile)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            }
            startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO)

        }
    }

}