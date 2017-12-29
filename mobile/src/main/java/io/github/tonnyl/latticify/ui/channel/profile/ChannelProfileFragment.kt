package io.github.tonnyl.latticify.ui.channel.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.format.DateUtils
import android.view.*
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.epoxy.LatticifyEpoxyAdapter
import io.github.tonnyl.latticify.ui.channel.edit.EditChannelActivity
import io.github.tonnyl.latticify.ui.channel.edit.EditChannelPresenter
import io.github.tonnyl.latticify.ui.channel.notifications.NotificationsActivity
import io.github.tonnyl.latticify.ui.channel.notifications.NotificationsPresenter
import io.github.tonnyl.latticify.util.AccessTokenManager
import kotlinx.android.synthetic.main.fragment_channel_details.*

/**
 * Created by lizhaotailang on 12/10/2017.
 */
class ChannelProfileFragment : Fragment(), ChannelProfileContract.View {

    private lateinit var mPresenter: ChannelProfileContract.Presenter

    private var mMenu: Menu? = null

    companion object {
        @JvmStatic
        fun newInstance() = ChannelProfileFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_channel_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        mPresenter.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mPresenter.unsubscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_channel_profile, menu)

        mMenu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
            }
            R.id.action_star_channel -> {
                mPresenter.starUnstarChannel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setPresenter(presenter: ChannelProfileContract.Presenter) {
        mPresenter = presenter
    }

    override fun showChannelDetails(channel: Channel) {
        channelNameTextView.text = getString(R.string.channel_name_with_hashtag).format(channel.name)
        purposeTextView.text = if (channel.purpose?.value.isNullOrEmpty()) getString(R.string.no_purpose_set) else channel.purpose?.value
        topicTextView.text = if (channel.topic?.value.isNullOrEmpty()) getString(R.string.no_topic_set) else channel.topic?.value
        creatorTextView.text = getString(R.string.channel_creation).format(channel.creator, DateUtils.getRelativeTimeSpanString(channel.created * 1000, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS))
        memberListTextView.text = getString(R.string.member_list).format(channel.numMembers ?: channel.members?.size)

        if (channel.creator == AccessTokenManager.getAccessToken().userId) {
            advancedLayout.visibility = View.VISIBLE
        }
        archiveDescriptionTextView.text = getString(R.string.archive_description).format(channel.name)

        editTextView.setOnClickListener {
            activity?.let {
                startActivity(Intent(context, EditChannelActivity::class.java).apply { putExtra(EditChannelPresenter.KEY_EXTRA_CHANNEL, channel) })
            }
        }

        notificationsTextView.setOnClickListener {
            activity?.let {
                startActivity(Intent(context, NotificationsActivity::class.java).apply { putExtra(NotificationsPresenter.KEY_EXTRA_CHANNEL, channel) })
            }
        }

    }

    override fun showPinnedItems(epoxyModels: Collection<EpoxyModel<*>>) {
        if (epoxyModels.isNotEmpty()) {
            pinnedItemsLayout.visibility = View.VISIBLE
            with(pinnedItemsRecyclerView) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = LatticifyEpoxyAdapter().apply {
                    addModels(epoxyModels)
                }
            }
        }
    }

    override fun setIfChannelStarred(starred: Boolean) {
        mMenu?.getItem(0)?.setIcon(if (starred) R.drawable.ic_star_black_24dp else R.drawable.ic_star_border_black_24dp)
        onPrepareOptionsMenu(mMenu)
    }

}