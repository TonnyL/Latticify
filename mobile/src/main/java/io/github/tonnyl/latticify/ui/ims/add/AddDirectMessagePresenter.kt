package io.github.tonnyl.latticify.ui.ims.add

/**
 * Created by lizhaotailang on 11/10/2017.
 */
class AddDirectMessagePresenter(view: AddDirectMessageContract.View) : AddDirectMessageContract.Presenter {

    private val mView = view

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {

    }

}