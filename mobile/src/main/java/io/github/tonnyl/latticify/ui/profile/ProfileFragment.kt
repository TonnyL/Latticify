package io.github.tonnyl.latticify.ui.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.epoxy.LatticifyEpoxyAdapter
import io.github.tonnyl.latticify.glide.GlideLoader
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Created by lizhaotailang on 08/10/2017.
 */
class ProfileFragment : Fragment(), ProfileContract.View {

    private lateinit var mPresenter: ProfileContract.Presenter

    private val mAdapter = LatticifyEpoxyAdapter()

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

        setHasOptionsMenu(true)

        mPresenter.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mPresenter.unsubscribe()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                activity.onBackPressed()
            }
        }
        return true
    }

    override fun setPresenter(presenter: ProfileContract.Presenter) {
        mPresenter = presenter
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
        GlideLoader.loadNormal(avatarImageView, url)
    }

    private fun initViews() {
        with(activity as ProfileActivity) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = mAdapter
    }

}