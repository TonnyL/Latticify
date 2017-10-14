package io.github.tonnyl.latticify.ui.channel.edit

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.Channel
import kotlinx.android.synthetic.main.fragment_edit_channel.*

/**
 * Created by lizhaotailang on 14/10/2017.
 */
class EditChannelFragment : Fragment(), EditChannelContract.View {

    private lateinit var mPresenter: EditChannelContract.Presenter

    companion object {
        @JvmStatic
        fun newInstance() = EditChannelFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_edit_channel, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        setHasOptionsMenu(true)

        mPresenter.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mPresenter.unsubscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.menu_done, menu)
    }

    override fun setPresenter(presenter: EditChannelContract.Presenter) {
        mPresenter = presenter
    }

    override fun showChanelInfo(channel: Channel) {
        nameEditText.setText(channel.name)
        channel.purpose?.let { purposeEditText.setText(it.value) }
        channel.topic?.let { topicEditText.setText(it.value) }
    }

}