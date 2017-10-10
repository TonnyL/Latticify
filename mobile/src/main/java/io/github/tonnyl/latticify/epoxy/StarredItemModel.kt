package io.github.tonnyl.latticify.epoxy

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
 * Created by lizhaotailang on 09/10/2017.
 */
@EpoxyModelClass(layout = R.layout.item_starred_item)
abstract class StarredItemModel : EpoxyModelWithHolder<StarredItemModel.StarredItemHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var itemOnClickListener: View.OnClickListener
    @EpoxyAttribute
    lateinit var starredItem: StarredPinnedItem

    override fun createNewHolder(): StarredItemHolder = StarredItemHolder()

    override fun bind(holder: StarredItemHolder?) {
        super.bind(holder)

        holder?.let {
            with(it) {
                itemLayout.setOnClickListener(itemOnClickListener)

                when {
                    starredItem.message != null -> {
                        avatarImageView.setImageResource(R.drawable.ic_message_black_24dp)
                        titleTextView.text = starredItem.message?.text ?: starredItem.message?.attachments?.getOrNull(0)?.let { "${it.title}\n${it.text}" } ?: run { "" }
                    }
                    starredItem.file != null -> {
                        avatarImageView.setImageResource(R.drawable.ic_folder_black_24dp)
                        titleTextView.text = starredItem.file?.title
                    }
                    starredItem.channel != null -> {
                        avatarImageView.setImageResource(R.drawable.ic_hashtag_black_24dp)
                        titleTextView.text = starredItem.channel
                    }
                }
            }
        }
    }

    class StarredItemHolder : EpoxyHolder() {

        lateinit var itemLayout: View
        lateinit var titleTextView: TextView
        lateinit var avatarImageView: ImageView

        override fun bindView(itemView: View?) {
            itemView?.let {
                with(it) {
                    itemLayout = findViewById(R.id.starredItemLayout)
                    titleTextView = findViewById(R.id.titleTextView)
                    avatarImageView = findViewById(R.id.avatarImageView)
                }
            }
        }

    }

}