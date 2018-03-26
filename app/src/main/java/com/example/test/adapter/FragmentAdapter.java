package com.example.test.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import java.util.List;

/**
 * @author dwl
 * @Title: FragmentAdapter
 * @date 18/3/9
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private List<String> titles;

    private List<Fragment> fragments;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> list) {
        super(fm);
        this.titles = list;
        this.fragments=fragments;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
