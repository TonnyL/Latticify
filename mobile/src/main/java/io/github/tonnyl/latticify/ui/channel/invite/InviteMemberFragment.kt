package io.github.tonnyl.latticify.ui.channel.invite

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.epoxy.LatticifyEpoxyAdapter
import kotlinx.android.synthetic.main.fragment_list.*

class InviteMemberFragment : Fragment(), InviteMemberContract.View {

    private lateinit var mPresenter: InviteMemberContract.Presenter
    private val mAdapter = LatticifyEpoxyAdapter()

    private var mIsLoading = false

    private var mMenu: Menu? = null

    companion object {
        fun newInstance() = InviteMemberFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            swipeRefreshLayout.setColorSchemeColors(it.getColor(R.color.colorAccent))
        }
        swipeRefreshLayout.setOnRefreshListener {
            mPresenter.fetchData()
            mIsLoading = true
        }

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = recyclerView?.adapter?.itemCount
                    val lastVisibleItemPosition = recyclerView?.layoutManager?.let {
                        (it as LinearLayoutManager).findLastVisibleItemPosition()
                    }

                    if (!mIsLoading
                            && totalItemCount != null
                            && lastVisibleItemPosition != null
                            && lastVisibleItemPosition == totalItemCount - 1) {
                        mPresenter.fetchDataOfNextPage()
                        mIsLoading = true
                    }
                }
            })
        }

        mPresenter.subscribe()

        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mPresenter.unsubscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        mMenu = menu
        inflater?.inflate(R.menu.menu_done_with_progress, menu)

        mMenu?.getItem(0)?.title = getString(R.string.menu_done_with_progress).format(0)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
            }
            R.id.action_done -> {
                (mPresenter as InviteMemberPresenter).inviteMember()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showData(epoxyModels: Collection<EpoxyModel<*>>) {
        recyclerView.visibility = View.VISIBLE
        emptyView.visibility = View.GONE
        errorView.visibility = View.GONE
        mAdapter.removeAllModels()
        mAdapter.addModels(epoxyModels)

        mIsLoading = false
    }

    override fun showDataOfNextPage(epoxyModels: Collection<EpoxyModel<*>>) {
        mAdapter.addModels(epoxyModels)

        mIsLoading = false
    }

    override fun addModel(epoxyModel: EpoxyModel<*>) {
        mAdapter.addModel(epoxyModel)
    }

    override fun setLoadingIndicator(loading: Boolean) {
        swipeRefreshLayout.isRefreshing = loading
        emptyView.visibility = View.GONE
        errorView.visibility = View.GONE
    }

    override fun showLoadingMore(loadingMore: Boolean) {
        mAdapter.showLoadingMore(loadingMore)
        mIsLoading = loadingMore
    }

    override fun showEmptyView() {
        emptyView.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun showErrorView() {
        errorView.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun setPresenter(presenter: InviteMemberContract.Presenter) {
        mPresenter = presenter
    }

    override fun updateProgress(currentSelected: Int) {
        mMenu?.getItem(0)?.title = getString(R.string.menu_done_with_progress).format(currentSelected)
    }

    override fun finish() {
        val intent = Intent().apply {
            putExtra(InviteMemberActivity.EXTRA_RESULT_INVITE_MEMBER, true)
        }
        activity?.setResult(Activity.RESULT_OK, intent)

        activity?.finish()
    }

}