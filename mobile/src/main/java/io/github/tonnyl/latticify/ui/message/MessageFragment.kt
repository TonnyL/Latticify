package io.github.tonnyl.latticify.ui.message

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.format.DateUtils
import android.view.*
import android.widget.Toast
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.Message
import io.github.tonnyl.latticify.data.User
import io.github.tonnyl.latticify.glide.GlideLoader
import kotlinx.android.synthetic.main.fragment_message.*

/**
 * Created by lizhaotailang on 08/10/2017.
 */
class MessageFragment : Fragment(), MessageContract.View {

    private lateinit var mPresenter: MessageContract.Presenter
    private lateinit var mMessage: Message

    companion object {
        @JvmStatic
        fun newInstance(): MessageFragment = MessageFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)
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
        inflater?.inflate(R.menu.menu_message_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
            }
            R.id.action_copy_text -> {
                context?.let { context ->
                    (mMessage.text
                            ?: mMessage.attachments?.getOrNull(0)?.let { "${it.title}\n${it.text}" })?.let {
                        val manager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clipData = ClipData.newPlainText("text", it)
                        manager.primaryClip = clipData

                        Toast.makeText(context, R.string.copied, Toast.LENGTH_SHORT).show()
                    } ?: run {
                        Toast.makeText(context, R.string.copy_failed, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            R.id.action_copy_link_to_message -> {
                mPresenter.copyLinkToMessage(mMessage.ts)
            }
            R.id.action_share_message -> {

            }
            R.id.action_star -> {

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

    override fun setPresenter(presenter: MessageContract.Presenter) {
        mPresenter = presenter
    }

    override fun showMessage(message: Message) {
        messengerNameTextView.text = message.username
        messageContentTextView.text = message.text ?: message.attachments?.getOrNull(0)?.let { "${it.title}\n${it.text}" } ?: ""
        messageTimeTextView.text = DateUtils.getRelativeTimeSpanString(message.ts.substringBefore(".").toLong() * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS)
        message.icons?.let {
            GlideLoader.loadAvatar(messengerAvatarImageView, it.image72)
        }

        mMessage = message
    }

    override fun showChannel(channel: Channel) {
        fromChannelTextView.text = getString(R.string.message_in_channel).format(channel.name)
    }

    override fun showUser(user: User) {
        messengerNameTextView.text = user.realName
        GlideLoader.loadAvatar(messengerAvatarImageView, user.profile.image192)
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

}