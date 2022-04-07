package net.frazionz.android.activity.ui.profile.tabs;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import net.frazionz.android.activity.ui.profile.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class ProfilePagerAdapter extends FragmentStatePagerAdapter {
    private final List<IPFragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final FragmentManager fm;
    private ProfileFragment profileFragment;
    private IPFragment mCurrentFragment;

    public ProfilePagerAdapter(FragmentManager fm, ProfileFragment profileFragment) {
        super(fm);
        this.fm = fm;
        this.profileFragment = profileFragment;
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(profileFragment.getContext());
        Intent i = new Intent("TAG_REFRESH");
        lbm.sendBroadcast(i);
    }

    @Override
    public IPFragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void reloadFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        for(Fragment fragment : fm.getFragments()){
            if(fragment instanceof IPFragment){
                IPFragment ipf = (IPFragment) fragment;
                ipf.reloadDataProfile();
            }
        }
    }

    public void addFragment(IPFragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        if(mFragmentList.size() == 1)
            mCurrentFragment = mFragmentList.get(0);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public IPFragment getCurrentFragment() {
        return mCurrentFragment;
    }
}
