package io.github.tonnyl.latticify.epoxy

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.User
import io.github.tonnyl.latticify.glide.GlideLoader

/**
 * Created by lizhaotailang on 11/10/2017.
 */
@EpoxyModelClass(layout = R.layout.item_user)
abstract class UserModel : EpoxyModelWithHolder<UserModel.UserHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var itemOnClickListener: View.OnClickListener
    @EpoxyAttribute
    lateinit var user: User

    override fun createNewHolder(): UserHolder = UserHolder()

    override fun bind(holder: UserHolder?) {
        super.bind(holder)

        holder?.let {
            with(it) {
                itemLayout?.setOnClickListener(itemOnClickListener)

                avatarImageView?.let {
                    GlideLoader.loadAvatar(it, user.profile.image192)
                }

                realNameTextView?.text = user.profile.realNameNormalized
                displayTextView?.text = user.profile.displayNameNormalized
                titleTextView?.text = user.profile.title

            }
        }
    }

    class UserHolder : EpoxyHolder() {

        var itemLayout: View? = null
        var avatarImageView: ImageView? = null
        var realNameTextView: TextView? = null
        var displayTextView: TextView? = null
        var titleTextView: TextView? = null

        override fun bindView(itemView: View?) {
            itemView?.let {
                with(it) {
                    itemLayout = findViewById(R.id.userItemLayout)
                    avatarImageView = findViewById(R.id.avatar_image_view)
                    realNameTextView = findViewById(R.id.realNameTextView)
                    displayTextView = findViewById(R.id.displayNameTextView)
                    titleTextView = findViewById(R.id.titleTextView)
                }
            }
        }
    }

}