package io.github.tonnyl.latticify.ui.channel

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.Channel
import io.github.tonnyl.latticify.data.Message
import io.github.tonnyl.latticify.epoxy.LatticifyEpoxyAdapter
import io.github.tonnyl.latticify.epoxy.LoadMoreModel_
import io.github.tonnyl.latticify.ui.channel.profile.ChannelProfileActivity
import io.github.tonnyl.latticify.ui.channel.profile.ChannelProfilePresenter
import io.github.tonnyl.latticify.ui.message.MessageActivity
import io.github.tonnyl.latticify.ui.message.MessagePresenter
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Created by lizhaotailang on 06/10/2017.
 *
 */
class ChannelFragment : Fragment(), ChannelContract.View {

    override var ignoreScrollChange: Boolean = false

    private lateinit var mPresenter: ChannelContract.Presenter

    private var mIsLoading = false
    private val mAdapter = LatticifyEpoxyAdapter()
    private val mLoadMoreModel = LoadMoreModel_()

    companion object {
        @JvmStatic
        fun newInstance(): ChannelFragment = ChannelFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_channel, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout.isEnabled = false
        swipeRefreshLayout.setColorSchemeColors(context.getColor(R.color.colorAccent))
        swipeRefreshLayout.setOnRefreshListener {
            mPresenter.fetchData()
        }

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
            adapter = mAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!ignoreScrollChange) {
                        val totalItemCount = recyclerView?.adapter?.itemCount
                        val lastVisibleItemPosition = recyclerView?.layoutManager?.let {
                            (it as LinearLayoutManager).findLastVisibleItemPosition()
                        }

                        if (!mIsLoading
                                && totalItemCount != null
                                && lastVisibleItemPosition != null
                                && lastVisibleItemPosition == totalItemCount - 1) {
                            mIsLoading = true
                            mPresenter.fetchDataOfNextPage()
                        }
                    }
                }
            })
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

        inflater?.inflate(R.menu.menu_channel, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            android.R.id.home -> {
                activity.onBackPressed()
            }
            R.id.action_view_details -> {
                mPresenter.viewDetails()
            }
            R.id.action_invite_members_to_channel -> {

            }
            R.id.action_directory -> {

            }
        }
        return true
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_copy_text -> {

            }
            R.id.action_share -> {

            }
        }

        return true
    }

    override fun setPresenter(presenter: ChannelContract.Presenter) {
        mPresenter = presenter
    }

    override fun showData(epoxyModels: Collection<EpoxyModel<*>>) {
        recyclerView.visibility = View.VISIBLE
        emptyView.visibility = View.GONE
        errorView.visibility = View.GONE
        mAdapter.removeAllModels()
        mAdapter.addModels(epoxyModels)
    }

    override fun showDataOfNextPage(epoxyModels: Collection<EpoxyModel<*>>) {
        mAdapter.addModels(epoxyModels)
    }

    override fun addModel(epoxyModel: EpoxyModel<*>) {
        mAdapter.addModel(epoxyModel)
    }

    override fun setLoadingIndicator(loading: Boolean) {
        swipeRefreshLayout.isRefreshing = loading
        emptyView.visibility = View.GONE
        errorView.visibility = View.GONE

        mIsLoading = loading
    }

    override fun showLoadingMore(loadingMore: Boolean) {
        if (loadingMore) {
            addModel(mLoadMoreModel)
        } else {
            mAdapter.removeModel(mLoadMoreModel)
        }

        mIsLoading = loadingMore
    }

    override fun showEmptyView() {
        emptyView.visibility = View.VISIBLE
    }

    override fun showErrorView() {
        errorView.visibility = View.VISIBLE
    }

    override fun gotoChannelDetails(channel: Channel) {
        context.startActivity(Intent(context, ChannelProfileActivity::class.java).apply { putExtra(ChannelProfilePresenter.KEY_EXTRA_CHANNEL, channel) },
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle())
    }

    override fun gotoMessageDetails(message: Message) {
        context.startActivity(Intent(context, MessageActivity::class.java).apply { putExtra(MessagePresenter.KEY_EXTRA_MESSAGE, message) },
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle())
    }

}