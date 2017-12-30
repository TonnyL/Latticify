package io.github.tonnyl.latticify.epoxy

import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.StarredPinnedItem

/**
 * Created by lizhaotailang on 12/10/2017.
 */
@EpoxyModelClass(layout = R.layout.item_pin)
abstract class PinnedItemModel : EpoxyModelWithHolder<PinnedItemModel.PinnedItemHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var itemClickListener: View.OnClickListener
    @EpoxyAttribute
    lateinit var pin: StarredPinnedItem

    override fun createNewHolder(): PinnedItemHolder = PinnedItemHolder()

    override fun bind(holder: PinnedItemHolder?) {
        super.bind(holder)

        holder?.let {
            with(it) {
                itemLayout?.setOnClickListener(itemClickListener)
                avatarImageView?.setImageResource(when (pin.type) {
                    "message" -> R.drawable.ic_message_black_24dp
                    "file" -> R.drawable.ic_folder_black_24dp
                    else -> R.drawable.ic_comment_black_24dp
                })
                pinCreatorTextView?.text = pin.createdBy
                pinTimeTextView?.text = DateUtils.getRelativeTimeSpanString(pin.created * 1000, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)
                pinContentTextView?.text = pin.message?.text ?: pin.comment?.comment ?: pin.file?.title
            }
        }
    }

    class PinnedItemHolder : EpoxyHolder() {

        var itemLayout: View? = null
        var avatarImageView: ImageView? = null
        var pinCreatorTextView: TextView? = null
        var pinTimeTextView: TextView? = null
        var pinContentTextView: TextView? = null

        override fun bindView(itemView: View?) {
            itemView?.let {
                with(it) {
                    itemLayout = findViewById(R.id.pinItemLayout)
                    avatarImageView = findViewById(R.id.avatar_image_view)
                    pinCreatorTextView = findViewById(R.id.pinCreatorTextView)
                    pinTimeTextView = findViewById(R.id.pinTimeTextView)
                    pinContentTextView = findViewById(R.id.pinContentTextView)
                }
            }
        }

    }

}