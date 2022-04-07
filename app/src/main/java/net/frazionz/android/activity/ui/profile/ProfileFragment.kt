package net.frazionz.android.activity.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import net.frazionz.android.R
import net.frazionz.android.activity.ui.profile.tabs.PInfosFragment
import net.frazionz.android.activity.ui.profile.tabs.PInvFragment
import net.frazionz.android.activity.ui.profile.tabs.ProfilePagerAdapter
import net.frazionz.android.activity.ui.shop.SItemsFragment
import net.frazionz.android.activity.ui.shop.ShopPagerAdapter
import net.frazionz.android.activity.ui.shop.ShopTypes
import net.frazionz.android.utils.FZUtils
import net.frazionz.android.utils.FZUtils.DownloadImageTask


class ProfileFragment : Fragment() {

    private val mRecyclerView: RecyclerView? = null
    var swiperefreshProfile: SwipeRefreshLayout? = null
    var instanceFragment: ProfileFragment? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        instanceFragment = this;
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        val progressBarLayout: LinearLayout = view.findViewById(R.id.progressbar)
        val pagerProfile: LinearLayout = view.findViewById(R.id.pagerProfile)
        Thread {
            Thread.sleep(800);
            val wRenderSkin: ImageView = view.findViewById(R.id.wviewRenderSkin)
            wRenderSkin.layout(0, 0, 10, 10)
            DownloadImageTask(wRenderSkin)
                .execute("https://auth.frazionz.net/skins/face.php?u="+FZUtils.getAuth().profile.userName)
            loadDataProfile(view)
            view.post(Runnable {
                val tabLayout = view.findViewById<View>(R.id.tabs) as TabLayout
                val viewPager = view.findViewById<View>(R.id.viewpager) as ViewPager
                val viewPageAdapter = ProfilePagerAdapter(fragmentManager, instanceFragment)
                viewPageAdapter.addFragment(PInfosFragment(), "Informations")
                viewPageAdapter.addFragment(PInvFragment(), "Inventaire")
                viewPager.adapter = viewPageAdapter
                tabLayout.post {
                    tabLayout.setupWithViewPager(viewPager)
                }
                viewPageAdapter.reloadFragment()
                viewPager.currentItem = 0
                tabLayout.contentDescription = "TEST"
                tabLayout.getTabAt(0)?.text = "Infos"
                tabLayout.getTabAt(0)?.setIcon(R.drawable.door_close)
                tabLayout.getTabAt(0)?.contentDescription = "Infos"
                val mLayoutManager = LinearLayoutManager(activity)
                mLayoutManager.orientation = LinearLayoutManager.VERTICAL
                viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))

                tabLayout.setOnTabSelectedListener(object : OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                        viewPager.currentItem = tab.position
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab) {}
                    override fun onTabReselected(tab: TabLayout.Tab) {}
                })

                swiperefreshProfile = view.findViewById(R.id.swiperefreshProfile)
                swiperefreshProfile?.isEnabled = false;

                val profileActionLogout: TextView = view.findViewById(R.id.profile_ActionLogout)
                val profileActionRefresh: TextView = view.findViewById(R.id.profile_ActionRefresh)
                profileActionLogout.setOnClickListener {
                    Toast.makeText(it.context, "Not Available", Toast.LENGTH_LONG).show()
                }
                profileActionLogout.setOnLongClickListener {
                    Toast.makeText(it.context, "Déconnexion", Toast.LENGTH_LONG).show()
                    return@setOnLongClickListener true
                }
                profileActionRefresh.setOnClickListener {
                    swiperefreshProfile?.isRefreshing = true;
                    loadDataProfile(view)
                }
                pagerProfile.visibility = View.VISIBLE
                progressBarLayout.visibility = View.GONE
            })
        }.start()

        return view
    }

    private fun loadDataProfile(view: View) {
        FZUtils.loadProfile(view.context, FZUtils.getAuth().profile)
        val wRenderSkin: ImageView? = view.findViewById(R.id.wviewRenderSkin)
        DownloadImageTask(wRenderSkin)
            .execute("https://auth.frazionz.net/skins/index.php?login="+FZUtils.getAuth().profile.userName)

        val profileOnlineStateLabel: TextView? = view.findViewById(R.id.profile_OnlineStateLabel)
        val profileUsernameLabel: TextView? = view.findViewById(R.id.profile_UsernameLabel)
        val profileRankLabel: TextView? = view.findViewById(R.id.profile_RankLabel)

        var isOnline: Boolean = false
        var drawableDotsState: Int = R.drawable.dots_offline
        if(FZUtils.getApiFactionProfile().has("isOnline")) {
            if (FZUtils.getApiFactionProfile().getInt("isOnline") == 1) {
                isOnline = true;
                drawableDotsState = R.drawable.dots_online;
            }
        }
        profileUsernameLabel?.text = FZUtils.getAuth().profile.userName
        profileOnlineStateLabel?.setCompoundDrawablesWithIntrinsicBounds(
            drawableDotsState,
            0,
            0,
            0
        )
        profileOnlineStateLabel?.setOnClickListener {
            if(isOnline)
                Toast.makeText(view.context, "Vous êtes en ligne.", Toast.LENGTH_LONG).show()
            else
                Toast.makeText(view.context, "Vous êtes hors ligne.", Toast.LENGTH_LONG).show()
        }
        profileRankLabel?.text = FZUtils.getAuth().profile.userRank


        swiperefreshProfile?.isRefreshing = false
    }



}
