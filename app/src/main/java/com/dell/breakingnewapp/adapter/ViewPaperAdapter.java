package com.dell.breakingnewapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dell.breakingnewapp.view.fragment.CountryFragment;
import com.dell.breakingnewapp.view.fragment.EntertaimentFragment;
import com.dell.breakingnewapp.view.fragment.HomeFragment;
import com.dell.breakingnewapp.view.fragment.PageMoreFragment;
import com.dell.breakingnewapp.view.fragment.WorldFragment;

public class ViewPaperAdapter extends FragmentPagerAdapter {


    public ViewPaperAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new HomeFragment();
        }
        else if (position == 1)
        {
            fragment = new CountryFragment();
        }
        else if (position == 2)
        {
            fragment = new WorldFragment();
        }
        else if (position == 3)
        {
            fragment = new EntertaimentFragment();
        }else if(position == 4){
            fragment = new PageMoreFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return null;
    }
}
