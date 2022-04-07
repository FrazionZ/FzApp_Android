package net.frazionz.android.activity.ui.shop

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.icu.math.BigDecimal.valueOf
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import net.frazionz.android.R
import net.frazionz.android.activity.ui.profile.CardProfileAdapter
import net.frazionz.android.activity.ui.profile.CardProfileData
import net.frazionz.android.activity.ui.profile.ProfileFragment
import net.frazionz.android.activity.ui.profile.tabs.IPFragment
import net.frazionz.android.utils.FZUtils


class SItemsFragment(var shopType: ShopTypes) : Fragment() {

    private var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_shop_items, container, false);
        reloadDataShop(root!!)
        return root
    }

    @Nullable
    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        root = view;
    }

    private fun reloadDataShop(root: View) {
        val listCardShopItems: ListView? = root.findViewById(R.id.list)
        val items: Array<out ShopItems>? = FZUtils.loadShopItems(root.context, shopType)
        if(listCardShopItems?.adapter != null)
            listCardShopItems.deferNotifyDataSetChanged()
        val cardItemsDataArrayList: ArrayList<CardShopItemData> = ArrayList<CardShopItemData>()

        if (items != null) {
            for(item in items){
                cardItemsDataArrayList.add(CardShopItemData(root.context, item))
            }
        }

        val adapter = root.context?.let { CardShopItemsAdapter(it, cardItemsDataArrayList) }
        adapter?.notifyDataSetChanged()
        listCardShopItems?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        root = null
    }


}