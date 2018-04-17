package io.github.tonnyl.latticify.ui.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.User
import io.github.tonnyl.latticify.glide.GlideLoader
import io.github.tonnyl.latticify.ui.chat.ChatActivity
import io.github.tonnyl.latticify.ui.chat.ChatPresenter
import kotlinx.android.synthetic.main.fragment_user_profile.*
import java.util.*

/**
 * Created by lizhaotailang on 08/10/2017.
 */
class ProfileFragment : Fragment(), ProfileContract.View {

    private lateinit var mPresenter: ProfileContract.Presenter

    private var mIsMe = false

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        fab.setOnClickListener {
            mPresenter.openIm()
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
        inflater?.inflate(R.menu.menu_user_profile, menu)

        menu?.getItem(0)?.isVisible = mIsMe
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
            }
            R.id.action_edit -> {

            }
            R.id.action_view_files -> {

            }
            R.id.action_invite_to_channel -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setPresenter(presenter: ProfileContract.Presenter) {
        mPresenter = presenter
    }

    override fun showData(user: User) {
        with(user) {
            GlideLoader.loadAvatar(avatar_image_view, user.profile.image192)

            name_text_view.text = user.name
            username_text_view.text = user.realName

            real_name_text_view.text = realName

            status_text_view.text = "${profile.statusEmoji} ${profile.statusText}"

            if (profile.phone == null && profile.email == null && profile.skype == null) {
                contact_card_view.visibility = View.GONE
            } else {
                phone_text_view.text = profile.phone
                email_text_view.text = profile.email
            }

            locale_text_view.text = locale

            val hours = (TimeZone.getDefault().rawOffset / 1000 - Math.abs(tzOffset)) / 3600
            timezone_text_view.text = when {
                hours > 0 -> {
                    if (hours == -1L)
                        context?.resources?.getQuantityString(R.plurals.timezone_ahead, hours.toInt(), tzLabel)
                    else
                        context?.resources?.getQuantityString(R.plurals.timezone_ahead, hours.toInt(), hours.toInt(), tzLabel)
                }
                hours == 0L -> {
                    context?.getString(R.string.timezone_same)?.format(tzLabel) ?: ""
                }
                else -> {
                    if (hours == -1L)
                        context?.resources?.getQuantityString(R.plurals.timezone_ahead, hours.toInt(), tzLabel)
                    else
                        context?.resources?.getQuantityString(R.plurals.timezone_ahead, hours.toInt(), -hours.toInt(), tzLabel)
                }
            }
        }
    }

    override fun setIsMe(isMe: Boolean) {
        mIsMe = isMe
    }

    override fun gotoChannel(channel: Channel) {
        context?.startActivity(Intent(context, ChatActivity::class.java).apply { putExtra(ChatPresenter.KEY_EXTRA_CHANNEL, channel) })
    }

    private fun initViews() {
        activity?.let {
            with(it as ProfileActivity) {
                setSupportActionBar(toolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
    }

}