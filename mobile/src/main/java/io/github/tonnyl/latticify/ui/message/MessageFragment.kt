package io.github.tonnyl.latticify.ui.message

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.format.DateUtils
import android.view.*
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.Message
import kotlinx.android.synthetic.main.fragment_message.*

/**
 * Created by lizhaotailang on 08/10/2017.
 */
class MessageFragment : Fragment(), MessageContract.View {

    private lateinit var mPresenter: MessageContract.Presenter

    companion object {
        @JvmStatic
        fun newInstance(): MessageFragment = MessageFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
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
        inflater?.inflate(R.menu.menu_message, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                activity.onBackPressed()
            }
        }
        return true
    }

    override fun setPresenter(presenter: MessageContract.Presenter) {
        mPresenter = presenter
    }

    override fun showMessage(message: Message) {
        fromChannelTextView.text = getString(R.string.message_in_channel).format("#general")
        messengerNameTextView.text = message.username
        messageContentTextView.text = message.text ?: message.attachments?.getOrNull(0)?.let { "${it.title}\n${it.text}" } ?: run { "" }
        messageTimeTextView.text = DateUtils.getRelativeTimeSpanString(message.ts.substringBefore(".").toLong() * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS)
    }

}