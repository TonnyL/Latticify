package io.github.tonnyl.latticify.ui.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v7.graphics.Palette
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.epoxy.LatticifyEpoxyAdapter
import io.github.tonnyl.latticify.glide.GlideLoader
import io.github.tonnyl.latticify.glide.OnPaletteProcessedCallback
import io.github.tonnyl.latticify.ui.channel.ChannelActivity
import io.github.tonnyl.latticify.ui.channel.ChannelPresenter
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Created by lizhaotailang on 08/10/2017.
 */
class ProfileFragment : Fragment(), ProfileContract.View {

    private lateinit var mPresenter: ProfileContract.Presenter

    private val mAdapter = LatticifyEpoxyAdapter()

    private var mIsMe = false

    companion object {
        @JvmStatic
        fun newInstance(): ProfileFragment = ProfileFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
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
                activity.onBackPressed()
            }
            R.id.action_edit -> {

            }
            R.id.action_view_files -> {

            }
            R.id.action_invite_to_channel -> {

            }
        }
        return true
    }

    override fun setPresenter(presenter: ProfileContract.Presenter) {
        mPresenter = presenter
    }

    override fun setIsMe(isMe: Boolean) {
        mIsMe = isMe
    }

    override fun showData(epoxyModels: Collection<EpoxyModel<*>>) {
        mAdapter.removeAllModels()
        mAdapter.addModels(epoxyModels)
    }

    override fun showTitle(title: String) {
        with(activity as ProfileActivity) {
            this.title = title
        }
    }

    override fun showAvatar(url: String) {

        GlideLoader.loadWithPalette(avatarImageView, url, object : OnPaletteProcessedCallback {

            override fun onPaletteGenerated(palette: Palette?) {
                palette?.vibrantSwatch?.let {
                    toolbar_layout.setExpandedTitleColor(it.bodyTextColor)
                }
            }

            override fun onPaletteNotAvailable() {

            }
        })
    }

    override fun gotoChannel(channel: Channel) {
        context.startActivity(Intent(context, ChannelActivity::class.java).apply { putExtra(ChannelPresenter.KEY_EXTRA_CHANNEL, channel) },
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle())
    }

    private fun initViews() {
        with(activity as ProfileActivity) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = mAdapter
    }

}