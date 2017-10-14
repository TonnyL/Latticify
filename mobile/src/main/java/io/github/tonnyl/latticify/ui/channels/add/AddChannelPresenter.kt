package io.github.tonnyl.latticify.ui.channels.add

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class AddChannelPresenter(view: AddChannelContract.View) : AddChannelContract.Presenter {

    private val mView = view

    init {
        mView.setPresenter(this)
    }


    override fun subscribe() {

    }

    override fun unsubscribe() {

    }
}