package io.github.tonnyl.latticify.ui.file

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.format.DateUtils
import android.view.*
import io.github.tonnyl.latticify.R
import io.github.tonnyl.latticify.data.File
import io.github.tonnyl.latticify.data.FileWrapper
import kotlinx.android.synthetic.main.fragment_file.*

/**
 * Created by lizhaotailang on 29/12/2017.
 */
class FileFragment : Fragment(), FileContract.View {

    private lateinit var mPresenter: FileContract.Presenter

    companion object {
        @JvmStatic
        fun newInstance() = FileFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPresenter.subscribe()

        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun setPresenter(presenter: FileContract.Presenter) {
        mPresenter = presenter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_file, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
            }
            R.id.action_add_reaction -> {

            }
            R.id.action_comment -> {

            }
            R.id.action_star_file -> {

            }
            R.id.action_share_file -> {

            }
            R.id.action_copy_link -> {

            }
            R.id.action_delete -> {

            }
            R.id.action_open_in_browser -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setLoadingIndicator(loading: Boolean) {
        if (loading) {
            loading_animation_view.visibility = View.VISIBLE
            content_web_view.visibility = View.GONE
        } else {
            loading_animation_view.cancelAnimation()
            loading_animation_view.visibility = View.GONE
            content_web_view.visibility = View.VISIBLE
        }
    }

    override fun showFetchDataError() {

    }

    override fun showFileData(file: File) {
        user_name_text_view.text = file.username
        file_title_text_view.text = file.title

        file_info_text_view.text = DateUtils.formatDateTime(context, file.created * 1000,
                DateUtils.FORMAT_SHOW_YEAR or
                        DateUtils.FORMAT_SHOW_DATE or
                        DateUtils.FORMAT_SHOW_WEEKDAY or
                        DateUtils.FORMAT_SHOW_TIME)

    }

    override fun showContent(wrapper: FileWrapper) {
        val html = """
            <!DOCTYPE html>
            <html>
            <head>
            <style>
            ${wrapper.contentHighlightCss}
            </style>
            </head>
            <body>
            ${wrapper.contentHighlightHtml}
            </body>
            </html>
            """
        content_web_view.loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
    }

}