package io.github.tonnyl.latticify.epoxy

import android.text.format.DateUtils
import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.File


/**
 * Created by lizhaotailang on 14/10/2017.
 */
@EpoxyModelClass(layout = R.layout.item_search_file)
abstract class SearchFileModel : EpoxyModelWithHolder<SearchFileModel.SearchFileHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var itemClickListener: View.OnClickListener
    @EpoxyAttribute
    lateinit var file: File

    override fun bind(holder: SearchFileHolder?) {
        super.bind(holder)

        holder?.let {
            with(it) {
                itemLayout?.setOnClickListener(itemClickListener)
                fileNameTextView?.text = file.name
                fileDescriptionTextView?.text = fileDescriptionTextView?.context?.getString(R.string.search_file_description)
                        ?.format(if (file.username.isNotEmpty()) file.user else file.user, DateUtils.getRelativeTimeSpanString(file.created * 1000, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS))
            }
        }
    }

    override fun createNewHolder(): SearchFileHolder = SearchFileHolder()

    class SearchFileHolder : EpoxyHolder() {

        var itemLayout: View? = null
        var fileNameTextView: TextView? = null
        var fileDescriptionTextView: TextView? = null

        override fun bindView(itemView: View?) {
            itemView?.let {
                with(it) {
                    itemLayout = findViewById(R.id.fileItemLayout)
                    fileNameTextView = findViewById(R.id.fileNameTextView)
                    fileDescriptionTextView = findViewById(R.id.fileDescriptionTextView)
                }
            }
        }
    }

}