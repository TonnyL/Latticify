package io.github.tonnyl.latticify.ui.ims.add

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.latticify.R

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class AddDirectMessageFragment : Fragment(), AddDirectMessageContract.View {

    private lateinit var mPresenter: AddDirectMessageContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_direct_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPresenter.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mPresenter.unsubscribe()
    }

    override fun setPresenter(presenter: AddDirectMessageContract.Presenter) {
        mPresenter = presenter
    }
}