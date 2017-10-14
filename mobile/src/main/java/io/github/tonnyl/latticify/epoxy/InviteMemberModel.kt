package io.github.tonnyl.latticify.epoxy

import android.support.design.widget.TextInputEditText
import android.view.View
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import io.github.tonnyl.latticify.R

/**
 * Created by lizhaotailang on 13/10/2017.
 */
@EpoxyModelClass(layout = R.layout.item_invite_members)
abstract class InviteMemberModel : EpoxyModelWithHolder<InviteMemberModel.InviteMemberHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var removeClickListener: View.OnClickListener
    @EpoxyAttribute
    var position = 0

    override fun createNewHolder(): InviteMemberHolder = InviteMemberHolder()

    override fun bind(holder: InviteMemberHolder?) {
        super.bind(holder)

        holder?.let {
            with(it) {
                if (position == 0) {
                    removeImageView?.visibility = View.GONE
                }
            }
        }
    }

    class InviteMemberHolder : EpoxyHolder() {

        var emailEditText: TextInputEditText? = null
        var removeImageView: ImageView? = null

        override fun bindView(itemView: View?) {
            itemView?.let {
                with(it) {
                    emailEditText = findViewById(R.id.emailEditText)
                    removeImageView = findViewById(R.id.removeImageView)
                }
            }
        }

    }

}