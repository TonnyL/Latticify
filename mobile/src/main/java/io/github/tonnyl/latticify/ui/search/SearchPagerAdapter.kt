package io.github.tonnyl.latticify.ui.search

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.github.tonnyl.latticify.R

/**
 * Created by lizhaotailang on 29/12/2017.
 */
class SearchPagerAdapter(context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val mTitles = arrayOf(context.getString(R.string.messages), context.getString(R.string.files))
    private val mFragments = mutableListOf<Fragment>()

    override fun getItem(position: Int): Fragment {
        if (position == 1) {
            return mFragments[1]
        }
        return mFragments[0]
    }

    override fun getCount(): Int = mTitles.size

    override fun getPageTitle(position: Int): CharSequence? = mTitles[position]

    fun addFragment(vararg fragments: Fragment) {
        mFragments.addAll(fragments)
    }

}