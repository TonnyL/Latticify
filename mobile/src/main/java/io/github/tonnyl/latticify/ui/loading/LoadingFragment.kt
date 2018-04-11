package io.github.tonnyl.latticify.ui.loading

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.latticify.R

class LoadingFragment : Fragment(), LoadingContract.View {

    private lateinit var mPresenter: LoadingContract.Presenter

    companion object {
        @JvmStatic
        fun newInstance() = LoadingFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPresenter.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mPresenter.unsubscribe()
    }

    override fun setPresenter(presenter: LoadingContract.Presenter) {
        mPresenter = presenter
    }

    override fun finishActivity() {
        activity?.finish()
    }

}