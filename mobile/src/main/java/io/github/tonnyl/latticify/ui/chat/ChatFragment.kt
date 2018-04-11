package io.github.tonnyl.latticify.ui.chat

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.TextView
import com.airbnb.epoxy.EpoxyModel
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import io.github.tonnyl.charles.Charles
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.Message
import io.github.tonnyl.latticify.epoxy.LatticifyEpoxyAdapter
import io.github.tonnyl.latticify.epoxy.LoadMoreModel_
import io.github.tonnyl.latticify.glide.CharlesGlideV4Engine
import io.github.tonnyl.latticify.glide.MatisseGlideV4Engine
import io.github.tonnyl.latticify.ui.channel.profile.ChannelProfileActivity
import io.github.tonnyl.latticify.ui.channel.profile.ChannelProfilePresenter
import io.github.tonnyl.latticify.ui.message.MessageActivity
import io.github.tonnyl.latticify.ui.message.MessagePresenter
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.layout_input.*

/**
 * Created by lizhaotailang on 06/10/2017.
 *
 */
class ChatFragment : Fragment(), ChatContract.View {

    override var ignoreScrollChange: Boolean = false

    private lateinit var mPresenter: ChatContract.Presenter

    private var mIsLoading = false
    private val mAdapter = LatticifyEpoxyAdapter()
    private val mLoadMoreModel = LoadMoreModel_()

    companion object {
        @JvmStatic
        fun newInstance(): ChatFragment = ChatFragment()

        val REQUEST_CHOOSE_IMAGE = 101
        val REQUEST_CHOOSE_FILE = 102

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_channel, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout.isEnabled = false
        context?.let {
            swipeRefreshLayout.setColorSchemeColors(it.getColor(R.color.colorAccent))
        }
        swipeRefreshLayout.setOnRefreshListener {
            mPresenter.fetchData()
        }

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

            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

        sendMessageImageView.setOnClickListener {
            Log.d("TAG", "${messageEditText.text.isNotBlank()}")
            if (!messageEditText.text.isNullOrBlank()) {
                mPresenter.sendMessage(messageEditText.text.toString())
                messageEditText.setText("")
            }
        }

        plusImageView.setOnClickListener {
            showBottomSheetDialog()
        }

        setHasOptionsMenu(true)

        mPresenter.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mPresenter.unsubscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.menu_channel, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            android.R.id.home -> {
                activity?.onBackPressed()
            }
            R.id.action_view_details -> {
                mPresenter.viewDetails()
            }
            R.id.action_invite_members_to_channel -> {

            }
            R.id.action_directory -> {

            }
        }
        return true
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_copy_text -> {

            }
            R.id.action_copy_link_to_message -> {

            }
            R.id.action_share_message -> {

            }
            R.id.action_add_reaction -> {

            }
            R.id.action_star -> {

            }
            R.id.action_remind_me -> {

            }
            R.id.action_mark_unread -> {

            }
            R.id.action_pin_to_conversation -> {

            }
            R.id.action_edit_message -> {

            }
            R.id.action_delete_message -> {

            }
        }

        return true
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
    }

    override fun gotoChannelDetails(channel: Channel) {
        activity?.let {
            context?.startActivity(Intent(context, ChannelProfileActivity::class.java).apply { putExtra(ChannelProfilePresenter.KEY_EXTRA_CHANNEL, channel) })
        }
    }

    override fun gotoMessageDetails(message: Message) {
        activity?.let {
            context?.startActivity(Intent(context, MessageActivity::class.java).apply { putExtra(MessagePresenter.KEY_EXTRA_MESSAGE, message) })
        }
    }

    private fun showBottomSheetDialog() {
        context?.let {
            val dialog = BottomSheetDialog(it)
            val view = layoutInflater.inflate(R.layout.layout_channel_bottom_sheet, null)
            dialog.setContentView(view)


            view.findViewById<TextView>(R.id.actionCamera).setOnClickListener {
                dialog.dismiss()
            }

            view.findViewById<TextView>(R.id.actionGallery).setOnClickListener {
                dialog.dismiss()

                // todo
                Matisse.from(this)
                        .choose(MimeType.allOf())
                        .imageEngine(MatisseGlideV4Engine())
                        .countable(true)
                        .maxSelectable(1)
                        .theme(R.style.Latticify_MatisseStyle)
                        .forResult(REQUEST_CHOOSE_IMAGE)
            }

            view.findViewById<TextView>(R.id.actionFile).setOnClickListener {
                dialog.dismiss()

                Charles.from(this)
                        .choose()
                        .imageEngine(CharlesGlideV4Engine())
                        .progressRate(true)
                        .maxSelectable(1)
                        .theme(R.style.Latticify_CharlesStyle)
                        .forResult(REQUEST_CHOOSE_FILE)
            }

            view.findViewById<TextView>(R.id.actionCommand).setOnClickListener {
                dialog.dismiss()
            }

            view.findViewById<TextView>(R.id.actionAt).setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }

}