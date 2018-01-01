package io.github.tonnyl.latticify.ui.invite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import io.github.tonnyl.latticify.R
import kotlinx.android.synthetic.main.fragment_invite.*

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class InviteFragment : Fragment(), InviteContract.View {

    private lateinit var mPresenter: InviteContract.Presenter

    companion object {
        @JvmStatic
        fun newInstance() = InviteFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_invite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email_edit_text.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                send_text_view.isEnabled = !s.isNullOrBlank()
                email_clear_image_view.visibility = if (s.isNullOrBlank()) View.GONE else View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        email_edit_text.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE
                    && !email_edit_text.text.isNullOrBlank()) {
                mPresenter.invite(email_edit_text.text.toString())
                true
            } else {
                false
            }
        }

        email_clear_image_view.setOnClickListener {
            email_edit_text.text.clear()
        }

        send_text_view.setOnClickListener {
            if (!email_edit_text?.text.isNullOrBlank()) {
                mPresenter.invite(email_edit_text.text.toString())
            }
        }

        setHasOptionsMenu(true)

        mPresenter.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mPresenter.unsubscribe()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            activity?.onBackPressed()
        }
        return true
    }

    override fun setPresenter(presenter: InviteContract.Presenter) {
        mPresenter = presenter
    }

}