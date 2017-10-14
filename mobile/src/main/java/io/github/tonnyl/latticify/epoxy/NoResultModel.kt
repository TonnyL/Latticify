package io.github.tonnyl.latticify.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import io.github.tonnyl.latticify.R

/**
 * Created by lizhaotailang on 13/10/2017.
 */
@EpoxyModelClass(layout = R.layout.item_lable)
abstract class NoResultModel : EpoxyModelWithHolder<NoResultModel.NoResultHolder>() {


    class NoResultHolder : EpoxyHolder() {
        override fun bindView(itemView: View?) {}
    }

}