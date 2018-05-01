package io.github.tonnyl.latticify.ui.channel.invite

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.latticify.R
import kotlinx.android.synthetic.main.activity_common.*

class InviteMemberActivity : AppCompatActivity() {

    private lateinit var mInviteMemberFragment: InviteMemberFragment

    companion object {
        const val EXTRA_RESULT_INVITE_MEMBER = "EXTRA_RESULT_INVITE_MEMBER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mInviteMemberFragment = InviteMemberFragment.newInstance()

        InviteMemberPresenter(mInviteMemberFragment, intent.getStringExtra(InviteMemberPresenter.KEY_EXTRA_CHANNEL_ID))

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mInviteMemberFragment, InviteMemberFragment::class.java.simpleName)
                .commit()

    }

}