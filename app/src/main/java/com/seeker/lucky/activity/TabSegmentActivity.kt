package com.seeker.lucky.activity

import android.view.View
import com.seeker.lucky.R
import com.seeker.lucky.app.LuckyFragment
import com.seeker.lucky.app.LuckyFragmentActivity
import com.seeker.lucky.extensions.findDrawableById
import com.seeker.lucky.extensions.instantiateFragment
import com.seeker.lucky.fragment.DataFragment
import com.seeker.lucky.fragment.HomeFragment
import com.seeker.lucky.fragment.ReportFragment
import com.seeker.lucky.fragment.SelfFragment
import com.seeker.lucky.tab.Tab
import com.seeker.lucky.tab.TabSegment
import com.seeker.lucky.tab.TabStateView
import kotlinx.android.synthetic.main.activity_tabsegment.*

/**
 * @author Seeker
 * @date 2019/1/12/012  10:55
 */
class TabSegmentActivity : LuckyFragmentActivity(),TabSegment.OnTabClickListener {

    override fun layoutResId(): Int  = R.layout.activity_tabsegment
    override fun fragmentContainerId(): Int = R.id.tabContainer

    override fun onViewCreated(contentView: View) {

        val tab1 = Tab.Builder()
                .setTabText("首页")
                .setDrawable(findDrawableById(R.mipmap.ic_tab_homepage_unselected),findDrawableById(R.mipmap.ic_tab_homepage))
                .setFragment(instantiateFragment<HomeFragment>())
                .Build()

        val tab2 = Tab.Builder()
                .setTabText("数据")
                .setDrawable(findDrawableById(R.mipmap.ic_tab_data_unselected),findDrawableById(R.mipmap.ic_tab_data))
                .setFragment(instantiateFragment<DataFragment>())
                .Build()

        val tab3 = Tab.Builder()
                .setTabText("报告")
                .setDrawable(findDrawableById(R.mipmap.ic_tab_report_unselected),findDrawableById(R.mipmap.ic_tab_report))
                .setFragment(instantiateFragment<ReportFragment>())
                .Build()

        val tab4 = Tab.Builder()
                .setTabText("我的")
                .setDrawable(findDrawableById(R.mipmap.ic_tab_my_unselected),findDrawableById(R.mipmap.ic_tab_my))
                .setFragment(instantiateFragment<SelfFragment>())
                .Build()

        tabSegment.addTabs(tab1, tab2, tab3, tab4)
                .setSelectedTabIndex(0)
                .setOnTabClickListener(this)
                .notifyTabChanged()
    }

    override fun onTabClick(stateView: TabStateView, position: Int) {
        displayFragment(stateView.tab.fragment as LuckyFragment)
    }

}
