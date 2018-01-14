package com.ar.simplecommerce.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ar.simplecommerce.R;
import com.ar.simplecommerce.view.fragment.CategoryFragment;
import com.ar.simplecommerce.view.fragment.ProductFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dimas on 14/01/2018.
 */

public class Home extends AppCompatActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.content)
    ViewPager content;

    CharSequence pagerTitle[] = {"Category","Product"};
    int numOfTabs;

    CategoryFragment category;
    ProductFragment product;
    PagerAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ButterKnife.bind(this);

        numOfTabs = pagerTitle.length;

        category = new CategoryFragment();
        product = new ProductFragment();
        adapter = new com.ar.simplecommerce.view.adapter.PagerAdapter(getSupportFragmentManager(),
                                                                     pagerTitle,
                                                                     numOfTabs,
                                                                     category,
                                                                     product
                                                                     );
        content.setAdapter(adapter);
        content.setOffscreenPageLimit(numOfTabs);
        content.setCurrentItem(0);

        tabLayout.setupWithViewPager(content);

    }
}
