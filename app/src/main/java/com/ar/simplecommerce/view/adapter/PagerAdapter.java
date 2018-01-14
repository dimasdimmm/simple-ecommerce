package com.ar.simplecommerce.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ar.simplecommerce.view.fragment.CategoryFragment;
import com.ar.simplecommerce.view.fragment.ProductFragment;

/**
 * Created by aderifaldi on 14/09/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private CharSequence Titles[];
    private int numOfTabs;
    private CategoryFragment category;
    private ProductFragment product;

    public PagerAdapter(FragmentManager fm, CharSequence mTitles[],
                                    int numOfTabs,
                                    CategoryFragment category,
                                    ProductFragment product) {
        super(fm);
        this.numOfTabs = numOfTabs;

        this.product = product;
        this.category = category;

        this.Titles = mTitles;

    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0){
            return category;
        }else {
            return product;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
