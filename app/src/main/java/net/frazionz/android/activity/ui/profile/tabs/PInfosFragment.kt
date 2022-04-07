package net.frazionz.android.activity.ui.profile.tabs

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import net.frazionz.android.R
import net.frazionz.android.activity.ui.profile.CardProfileAdapter
import net.frazionz.android.activity.ui.profile.CardProfileData
import net.frazionz.android.activity.ui.profile.ProfileFragment
import net.frazionz.android.utils.FZUtils


class PInfosFragment() : IPFragment() {

    private var root: View? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_profile_infos, container, false);
        reloadDataProfile()
        return root
    }

    @Nullable
    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        root = view;
    }

    override fun reloadDataProfile() {
        val listCardProfileInfos: ListView? = root?.findViewById(R.id.list)
        var money: String = "0.00 Coins"
        var firstJoin: String = "N/A"
        var lastJoin: String = "N/A"
        if(FZUtils.getApiFactionProfile().has("money"))
            money= FZUtils.conversMoney(FZUtils.getApiFactionProfile().getString("money"))+" Coins"
        if(FZUtils.getApiFactionProfile().has("firstJoinAt"))
            firstJoin = FZUtils.conversDate(FZUtils.getApiFactionProfile().getString("firstJoinAt"))
        if(FZUtils.getApiFactionProfile().has("lastJoinAt"))
            lastJoin = FZUtils.conversDate(FZUtils.getApiFactionProfile().getString("lastJoinAt"))

        if(listCardProfileInfos?.adapter != null)
            listCardProfileInfos.deferNotifyDataSetChanged()
        val cardProfileDataArrayList: ArrayList<CardProfileData> = ArrayList<CardProfileData>()
        cardProfileDataArrayList.add(CardProfileData(root?.context, R.drawable.envelope,"E-Mail", FZUtils.getAuth().profile.userMail))
        cardProfileDataArrayList.add(CardProfileData(root?.context, R.drawable.money,"Money", money))
        cardProfileDataArrayList.add(CardProfileData(root?.context, R.drawable.ic_baseline_fingerprint_24,"UUID", FZUtils.getAuth().profile.uuid))
        cardProfileDataArrayList.add(CardProfileData(root?.context, R.drawable.pencil,"Compte crée le", FZUtils.getAuth().profile.created_at))
        cardProfileDataArrayList.add(CardProfileData(root?.context, R.drawable.door_close,"Serveur rejoins le", firstJoin))
        cardProfileDataArrayList.add(CardProfileData(root?.context, R.drawable.door_open,"Dernière connexion le", lastJoin))
        val adapter = root?.context?.let { CardProfileAdapter(it, cardProfileDataArrayList) }
        adapter?.notifyDataSetChanged()
        listCardProfileInfos?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        root = null
    }


}