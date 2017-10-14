package io.github.tonnyl.latticify.epoxy

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import io.github.tonnyl.latticify.R

/**
 * Created by lizhaotailang on 10/10/2017.
 */
@EpoxyModelClass(layout = R.layout.item_profile)
abstract class ProfileItemModel : EpoxyModelWithHolder<ProfileItemModel.ProfileItemHolder>() {

    @EpoxyAttribute
    lateinit var title: String
    @EpoxyAttribute
    var descriptionId: Int = 0
    @EpoxyAttribute
    var icon1ResId: Int = 0
    @EpoxyAttribute
    var icon2ResId: Int = 0
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var itemClickListener: View.OnClickListener

    override fun createNewHolder(): ProfileItemHolder = ProfileItemHolder()

    override fun bind(holder: ProfileItemHolder?) {
        super.bind(holder)

        holder?.let {
            with(it) {
                itemLayout?.setOnClickListener(itemClickListener)

                contentTextView?.text = title
                descriptionTextView?.text = contentTextView?.context?.getString(descriptionId)

                if (icon1ResId != 0) {
                    icon1ImageView?.setImageResource(icon1ResId)
                } else {
                    icon1ImageView?.visibility = View.GONE
                }

                if (icon2ResId != 0) {
                    icon2ImageView?.setImageResource(icon2ResId)
                } else {
                    icon2ImageView?.visibility = View.GONE
                }
            }
        }
    }

    class ProfileItemHolder : EpoxyHolder() {

        var itemLayout: View? = null
        var icon1ImageView: ImageView? = null
        var icon2ImageView: ImageView? = null
        var contentTextView: TextView? = null
        var descriptionTextView: TextView? = null

        override fun bindView(itemView: View?) {
            itemView?.let {
                with(it) {
                    itemLayout = findViewById(R.id.profileItemLayout)
                    icon1ImageView = findViewById(R.id.icon1ImageView)
                    icon2ImageView = findViewById(R.id.icon2ImageView)
                    contentTextView = findViewById(R.id.contentTextView)
                    descriptionTextView = findViewById(R.id.descriptionTextView)
                }
            }
        }
    }

}