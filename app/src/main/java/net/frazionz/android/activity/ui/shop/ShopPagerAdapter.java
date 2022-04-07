package net.frazionz.android.activity.ui.shop;

import android.content.Intent;
import android.os.Build;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import net.frazionz.android.activity.ui.profile.ProfileFragment;
import net.frazionz.android.activity.ui.profile.tabs.IPFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShopPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final FragmentManager fm;
    private ShopFragment shopFragment;
    private Fragment mCurrentFragment;

    public ShopPagerAdapter(FragmentManager fm, ShopFragment shopFragment) {
        super(fm);
        this.fm = fm;
        this.shopFragment = shopFragment;
    }

    @Override
    public Fragment getItem(int position) {
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

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        if(mFragmentList.size() == 1)
            mCurrentFragment = mFragmentList.get(0);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }
}
