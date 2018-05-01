package io.github.tonnyl.latticify.ui.channels.create

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.ui.channels.create.CreateChannelPresenter.Companion.KEY_CREATE_CHANNEL
import io.github.tonnyl.latticify.ui.chat.ChatActivity
import io.github.tonnyl.latticify.ui.chat.ChatPresenter
import kotlinx.android.synthetic.main.fragment_add_channel.*

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class CreateChannelFragment : Fragment(), CreateChannelContract.View {

    private lateinit var mPresenter: CreateChannelContract.Presenter

    companion object {
        @JvmStatic
        fun newInstance() = CreateChannelFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_channel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        publicChannelTrigger.setOnClickListener {
            publicSwitch.isChecked = !publicSwitch.isChecked
            channelPublicDescTextView.text = getString(if (publicSwitch.isChecked) R.string.channel_public_description else R.string.channel_private_description)
        }

        setHasOptionsMenu(true)

        mPresenter.subscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.menu_done, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
            }
            R.id.action_done -> {
                if (!channelNameEditText.text.isNullOrBlank()) {
                    mPresenter.createChannelOrGroup(activity?.intent?.getBooleanExtra(KEY_CREATE_CHANNEL, true) == true, channelNameEditText.text.toString())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mPresenter.unsubscribe()
    }

    override fun setPresenter(presenter: CreateChannelContract.Presenter) {
        mPresenter = presenter
    }

    override fun showChannelOrGroupCreated(channel: Channel) {
        val intent = Intent(activity, ChatActivity::class.java).apply {
            putExtra(ChatPresenter.KEY_EXTRA_CHANNEL, channel)
            putExtra(ChatPresenter.KEY_EXTRA_IS_IM, false)
        }
        startActivity(intent)
        activity?.finish()
    }

}