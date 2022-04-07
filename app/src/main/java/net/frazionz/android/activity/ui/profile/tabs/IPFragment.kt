package net.frazionz.android.activity.ui.profile.tabs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import net.frazionz.android.R

abstract class IPFragment: Fragment() {

    private var instanceFragment: IPFragment? = null

    abstract fun reloadDataProfile()

    init {
        instanceFragment = this;
    }

    var r: FragmentReceiver? = null

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(r!!)
    }

    override fun onResume() {
        super.onResume()
        r = instanceFragment?.let { FragmentReceiver(it) }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            r!!,
            IntentFilter("TAG_REFRESH")
        )
    }

    class FragmentReceiver(private val instanceFrag: IPFragment) : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            instanceFrag.reloadDataProfile()
        }
    }

}