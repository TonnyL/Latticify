package io.github.tonnyl.latticify.epoxy

import android.support.annotation.StringRes
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import io.github.tonnyl.latticify.R

@EpoxyModelClass(layout = R.layout.item_label)
abstract class LabelModel : EpoxyModelWithHolder<LabelModel.LabelHolder>() {

    @EpoxyAttribute
    @StringRes
    var labelResId: Int = R.string.private_file

    override fun createNewHolder(): LabelHolder = LabelHolder()

    override fun bind(holder: LabelModel.LabelHolder) {
        super.bind(holder)

        with(holder) {
            labelTextView?.text = labelTextView?.context?.getString(labelResId)
        }
    }

    class LabelHolder : EpoxyHolder() {

        var labelTextView: AppCompatTextView? = null

        override fun bindView(itemView: View?) {
            labelTextView = itemView?.findViewById(R.id.labelTextView)
        }

    }

}