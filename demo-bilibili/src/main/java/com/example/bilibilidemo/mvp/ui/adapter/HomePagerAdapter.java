package com.example.bilibilidemo.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bilibilidemo.R;
import com.example.bilibilidemo.mvp.ui.fragment.HomeLiveFragment;
import com.example.bilibilidemo.mvp.ui.fragment.HomeRecommendedFragment;

/**
 * author: 小川
 * Date: 2019/7/23
 * Description:
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES;
    private Fragment[] fragments;

    public HomePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getResources().getStringArray(R.array.sections);
        fragments = new Fragment[TITLES.length];
    }


    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    fragments[position] = HomeLiveFragment.newInstance();
                    break;
//                case 1:
//                    fragments[position] = HomeRecommendedFragment.newInstance();
//                    break;
//                case 2:
//                    fragments[position] = HomeBangumiFragment.newInstance();
//                    break;
//                case 3:
//                    fragments[position] = HomeRegionFragment.newInstance();
//                    break;
//                case 4:
//                    fragments[position] = HomeAttentionFragment.newInstance();
//                    break;
//                case 5:
//                    fragments[position] = HomeDiscoverFragment.newInstance();
//                    break;
                default:
                    break;
            }
        }
        return fragments[position];
    }


    @Override
    public int getCount() {
        return 1;
        //        return TITLES.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}

