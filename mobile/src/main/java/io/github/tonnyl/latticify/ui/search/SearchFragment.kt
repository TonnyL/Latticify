package io.github.tonnyl.latticify.ui.search

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
import io.github.tonnyl.latticify.ui.search.files.SearchFilesFragment
import io.github.tonnyl.latticify.ui.search.files.SearchFilesPresenter
import io.github.tonnyl.latticify.ui.search.messages.SearchMessagesFragment
import io.github.tonnyl.latticify.ui.search.messages.SearchMessagesPresenter
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * Created by lizhaotailang on 24/09/2017.
 */
class SearchFragment : Fragment(), SearchContract.View {

    private lateinit var mPresenter: SearchContract.Presenter
    private var mSearchMessagesPresenter: SearchMessagesPresenter? = null
    private var mSearchFilesPresenter: SearchFilesPresenter? = null

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        search_edit_text.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                search_clear_image_view.visibility = if (s.isNullOrBlank()) View.GONE else View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        search_edit_text.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                with(search_edit_text.text.toString()) {
                    mSearchMessagesPresenter?.fetchSearchResults(this)
                    mSearchFilesPresenter?.fetchSearchResults(this)
                }
                true
            } else {
                false
            }
        }

        search_clear_image_view.setOnClickListener {
            search_edit_text.text.clear()
        }

        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setPresenter(presenter: SearchContract.Presenter) {
        mPresenter = presenter
    }

    private fun initViews() {
        with(activity as SearchActivity) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        tab_layout.setupWithViewPager(view_pager)
        view_pager.offscreenPageLimit = 2

        context?.let {
            val messagesFragment = SearchMessagesFragment.newInstance()
            mSearchMessagesPresenter = SearchMessagesPresenter(messagesFragment)

            val filesFragment = SearchFilesFragment.newInstance()
            mSearchFilesPresenter = SearchFilesPresenter(filesFragment)

            val adapter = SearchPagerAdapter(it, childFragmentManager)
            adapter.addFragment(messagesFragment, filesFragment)
            view_pager.adapter = adapter
        }
    }

}