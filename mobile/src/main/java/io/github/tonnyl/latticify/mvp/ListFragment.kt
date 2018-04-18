package io.github.tonnyl.latticify.mvp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.epoxy.LatticifyEpoxyAdapter
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Created by lizhaotailang on 19/09/2017.
 */
abstract class ListFragment : Fragment(), ListContract.View {

    private lateinit var mPresenter: ListContract.Presenter
    private val mAdapter = LatticifyEpoxyAdapter()

    private var mIsLoading = false

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
                    if (!ignoreScrollChange) {
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
                }
            })
        }

        mPresenter.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mPresenter.unsubscribe()
    }

    override fun setPresenter(presenter: ListContract.Presenter) {
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

    override fun gotoActivity(intent: Intent) {
        activity?.let {
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(it).toBundle())
        }
    }

}