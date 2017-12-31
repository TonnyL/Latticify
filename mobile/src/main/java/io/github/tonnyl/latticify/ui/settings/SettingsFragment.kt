package io.github.tonnyl.latticify.ui.settings

import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.MenuItem
import android.view.View
import io.github.tonnyl.latticify.R
import kotlinx.android.synthetic.main.activity_common.*

/**
 * Created by lizhaotailang on 31/12/2017.
 */
class SettingsFragment : PreferenceFragmentCompat(), SettingsContract.View {

    private lateinit var mPresenter: SettingsContract.Presenter

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings_screen)

        initViews()

        findPreference(getString(R.string.key_edit_profile)).onPreferenceClickListener = Preference.OnPreferenceClickListener {

            true
        }

        findPreference(getString(R.string.key_availability)).onPreferenceClickListener = Preference.OnPreferenceClickListener {

            true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setPresenter(presenter: SettingsContract.Presenter) {
        mPresenter = presenter
    }

    private fun initViews() {
        activity?.let {
            with(it as SettingsActivity) {
                setSupportActionBar(toolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
    }

}