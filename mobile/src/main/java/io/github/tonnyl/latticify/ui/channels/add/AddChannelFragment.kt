package io.github.tonnyl.latticify.ui.channels.add

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import io.github.tonnyl.latticify.R

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class AddChannelFragment : Fragment(), AddChannelContract.View {

    private lateinit var mPresenter: AddChannelContract.Presenter

    companion object {
        @JvmStatic
        fun newInstance() = AddChannelFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_add_channel, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        mPresenter.subscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.menu_done, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                activity.onBackPressed()
            }
            R.id.action_done -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mPresenter.unsubscribe()
    }

    override fun setPresenter(presenter: AddChannelContract.Presenter) {
        mPresenter = presenter
    }

}