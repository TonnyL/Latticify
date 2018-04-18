package io.github.tonnyl.latticify.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import io.github.tonnyl.latticify.R

/**
 * Created by lizhaotailang on 24/09/2017.
 */
@EpoxyModelClass(layout = R.layout.item_load_more)
abstract class LoadingModel : EpoxyModelWithHolder<LoadingModel.LoadMoreHolder>() {

    override fun createNewHolder(): LoadMoreHolder = LoadMoreHolder()

    class LoadMoreHolder : EpoxyHolder() {

        override fun bindView(itemView: View?) {

        }
    }

}