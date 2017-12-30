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
import io.github.tonnyl.latticify.data.SearchedMessageMatch

/**
 * Created by lizhaotailang on 14/10/2017.
 */
@EpoxyModelClass(layout = R.layout.item_search_message)
abstract class SearchedMessageModel : EpoxyModelWithHolder<SearchedMessageModel.SearchMessageHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var itemClickListener: View.OnClickListener
    @EpoxyAttribute
    lateinit var message: SearchedMessageMatch

    override fun createNewHolder(): SearchMessageHolder = SearchMessageHolder()

    override fun bind(holder: SearchMessageHolder?) {
        super.bind(holder)

        holder?.let {
            with(it) {
                itemLayout?.setOnClickListener(itemClickListener)

                messageToTextView?.text = message.channel.name
                timeTextView?.text = DateUtils.getRelativeTimeSpanString(message.ts.substringBefore(".").toLong(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)
                usernameTextView?.text = message.username
                messageContentTextView?.text = message.text

            }
        }
    }

    class SearchMessageHolder : EpoxyHolder() {

        var itemLayout: View? = null
        var messageToTextView: TextView? = null
        var timeTextView: TextView? = null
        var avatarImageView: ImageView? = null
        var usernameTextView: TextView? = null
        var messageContentTextView: TextView? = null

        override fun bindView(itemView: View?) {
            itemView?.let {
                with(it) {
                    itemLayout = findViewById(R.id.messageLayout)
                    messageToTextView = findViewById(R.id.messageToTextView)
                    timeTextView = findViewById(R.id.timeTextView)
                    avatarImageView = findViewById(R.id.avatar_image_view)
                    usernameTextView = findViewById(R.id.usernameTextView)
                    messageContentTextView = findViewById(R.id.messageContentTextView)
                }
            }
        }
    }

}