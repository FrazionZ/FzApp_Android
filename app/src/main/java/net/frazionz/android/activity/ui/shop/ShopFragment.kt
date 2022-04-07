package net.frazionz.android.activity.ui.shop

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import net.frazionz.android.R
import net.frazionz.android.utils.FZUtils
import java.util.concurrent.Executors


class ShopFragment : Fragment() {

    private var instanceFragment: ShopFragment? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        instanceFragment = this;
        val view: View = inflater.inflate(R.layout.fragment_shop, container, false)
        val progressBarLayout: LinearLayout = view.findViewById(R.id.progressbar)
        var test: Array<out ShopTypes>? = null
        Thread {
            Thread.sleep(800);
            test = FZUtils.loadShopType(view.context)
            view.post(Runnable {
                val tabLayout = view.findViewById<View>(R.id.tabs) as TabLayout
                val viewPager = view.findViewById<View>(R.id.viewpager) as ViewPager
                val viewPageAdapter = ShopPagerAdapter(fragmentManager, instanceFragment)
                if (test != null) {
                    for (shopTypes in test!!)
                        viewPageAdapter.addFragment(SItemsFragment(shopTypes), shopTypes.typeName)
                }
                viewPager.adapter = viewPageAdapter
                tabLayout.post {
                    tabLayout.setupWithViewPager(viewPager)
                }
                viewPageAdapter.reloadFragment()
                viewPager.visibility = View.VISIBLE
                progressBarLayout.visibility = View.GONE
            })
        }.start()


        return view
    }
}