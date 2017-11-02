package io.github.tonnyl.latticify.ui.channel.notifications

import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.MenuItem
import android.view.View
import io.github.tonnyl.latticify.R

/**
 * Created by lizhaotailang on 14/10/2017.
 */
class NotificationsFragment : PreferenceFragmentCompat(), NotificationsContract.View {

    private lateinit var mPresenter: NotificationsContract.Presenter

    companion object {
        @JvmStatic
        fun newInstance() = NotificationsFragment()
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

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.fragment_notifications)

        findPreference("key_mute_channel").onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->

            true
        }

        findPreference("key_ignore_at").onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->

            true
        }

        findPreference("key_notification_types").onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->

            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
            }
        }
        return true
    }

    override fun setPresenter(presenter: NotificationsContract.Presenter) {
        mPresenter = presenter
    }

}