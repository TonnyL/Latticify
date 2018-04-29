package io.github.tonnyl.latticify.ui.channel.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.widget.Toast
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.repository.UserPoolRepository
import io.github.tonnyl.latticify.epoxy.LatticifyEpoxyAdapter
import io.github.tonnyl.latticify.ui.channel.edit.EditChannelActivity
import io.github.tonnyl.latticify.ui.channel.edit.EditChannelPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_channel_details.*
import java.text.DateFormat
import java.util.*

/**
 * Created by lizhaotailang on 12/10/2017.
 */
class ChannelProfileFragment : Fragment(), ChannelProfileContract.View {

    private lateinit var mPresenter: ChannelProfileContract.Presenter

    private var mMenu: Menu? = null
    private val mCompositeDisposable = CompositeDisposable()

    companion object {
        @JvmStatic
        fun newInstance() = ChannelProfileFragment()

        const val REQUEST_EDIT_CHANNEL = 101
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_EDIT_CHANNEL) {
                val update = data?.getBooleanExtra(EditChannelActivity.EXTRA_RESULT_UPDATE, false)
                if (update == true) {
                    mPresenter.fetchLastedInfo()
                }
                Log.d("XXXX", "XXXX")
            }
        }
    }

    override fun setPresenter(presenter: ChannelProfileContract.Presenter) {
        mPresenter = presenter
    }

    override fun showChannelDetails(channel: Channel) {
        channelNameTextView.text = getString(R.string.channel_name_with_hashtag).format(channel.name)
        purposeTextView.text = if (channel.purpose?.value.isNullOrEmpty()) getString(R.string.no_purpose_set) else channel.purpose?.value
        topicTextView.text = if (channel.topic?.value.isNullOrEmpty()) getString(R.string.no_topic_set) else channel.topic?.value
        memberListTextView.text = getString(R.string.member_list).format(channel.numMembers
                ?: channel.members?.size)

        channel.creator?.let {
            val disposable = UserPoolRepository.getUser(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        creatorTextView.text = getString(R.string.channel_creation).format(it.profile.displayName, DateFormat.getInstance().format(Date(channel.created * 1000)))
                    }, {

                    })
            mCompositeDisposable.add(disposable)
        }

        archiveDescriptionTextView.text = getString(R.string.archive_description).format(channel.name)

        editTextView.setOnClickListener {
            startActivityForResult(Intent(context, EditChannelActivity::class.java).apply {
                putExtra(EditChannelPresenter.KEY_EXTRA_CHANNEL, channel)
            }, REQUEST_EDIT_CHANNEL)
        }

        leaveTextView.setOnClickListener {
            mPresenter.leaveChannel()
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

    override fun showChannelArchived() {
        Toast.makeText(context, getString(R.string.channel_archived), Toast.LENGTH_SHORT).show()

        val result = Intent().apply {
            putExtra(ChannelProfileActivity.EXTRA_RESULT_ARCHIVE, true)
        }

        activity?.let {
            it.setResult(Activity.RESULT_OK, result)
            it.finish()
        }

    }

    override fun showLeftChannel() {
        Toast.makeText(context, getString(R.string.channel_left), Toast.LENGTH_SHORT).show()

        val result = Intent().apply {
            putExtra(ChannelProfileActivity.EXTRA_RESULT_LEAVE, true)
        }

        activity?.let {
            it.setResult(Activity.RESULT_OK, result)
            it.finish()
        }

    }

    override fun showArchiveOptions(boolean: Boolean) {
        if (boolean) {
            advancedLayout.visibility = View.VISIBLE

            archiveTextView.setOnClickListener {
                mPresenter.archiveChannel()
            }
        } else {
            advancedLayout.visibility = View.GONE
        }
    }

    override fun showLeaveOption(boolean: Boolean) {
        leaveTextView.visibility = if (boolean) View.VISIBLE else View.GONE
    }

    override fun showEditOption(boolean: Boolean) {
        editTextView.visibility = if (boolean) View.VISIBLE else View.GONE
    }

}