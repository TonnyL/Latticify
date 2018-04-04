package io.github.tonnyl.latticify.epoxy

import android.support.annotation.StringRes
import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import io.github.tonnyl.latticify.R

/**
 * Created by lizhaotailang on 13/10/2017.
 */
@EpoxyModelClass(layout = R.layout.item_lable)
abstract class LabelModel : EpoxyModelWithHolder<LabelModel.LabelHolder>() {

    @EpoxyAttribute
    @StringRes
    var labelId: Int = 0

    override fun bind(holder: LabelHolder) {
        super.bind(holder)

        if (labelId != 0) {
            holder.labelTextView?.setText(labelId)
        }
    }

    override fun createNewHolder(): LabelHolder = LabelHolder()

    class LabelHolder : EpoxyHolder() {

        var labelTextView: TextView? = null

        override fun bindView(itemView: View?) {
            labelTextView = itemView?.findViewById(R.id.labelTextView)
        }

    }

}