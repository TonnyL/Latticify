package io.github.tonnyl.latticify.ui.snooze

import android.os.Bundle
import android.support.v7.preference.ListPreference
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.MenuItem
import android.view.View
import io.github.tonnyl.latticify.R
import kotlinx.android.synthetic.main.activity_common.*

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class SnoozeNotificationsFragment : PreferenceFragmentCompat(), SnoozeNotificationsContract.View {

    private lateinit var mPresenter: SnoozeNotificationsContract.Presenter

    companion object {
        @JvmStatic
        fun newInstance() = SnoozeNotificationsFragment()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.snooze_notifications_screen)

        initViews()

        findPreference("key_snooze_notifications").setOnPreferenceChangeListener { preference, newValue ->
            if (newValue as Boolean) {
                mPresenter.setSnooze(getNumMinutes())
            } else {
                mPresenter.endSnooze()
            }

            true
        }

        findPreference("key_snooze_notifications_list").setOnPreferenceChangeListener { preference, newValue ->
            mPresenter.setSnooze(getNumMinutes())

            true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun setPresenter(presenter: SnoozeNotificationsContract.Presenter) {
        mPresenter = presenter
    }

    override fun updateSnoozeSwitch(enabled: Boolean) {
        findPreference("key_snooze_notifications").isEnabled = enabled
    }

    private fun initViews() {
        activity?.let {
            with(it as SnoozeNotificationsActivity) {
                setSupportActionBar(toolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    private fun getNumMinutes(): Int {
        return when ((findPreference("key_snooze_notifications_list") as ListPreference).value) {
            "0" -> 20 // 20 minutes
            "1" -> 60 // 1 hour
            "2" -> 2 * 60 // 2 hours
            "3" -> 4 * 60 // 4 hours
            "4" -> 8 * 60 // 8 hours
            else -> 24 * 60 // 24 hours
        }
    }

}