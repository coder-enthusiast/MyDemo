package com.jqk.mydemo.nestedscrolling;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.jqk.mydemo.R;

public class NestedScrollingActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private String[] mTitles = new String[] { "简介", "评价", "相关" };
    private FragmentPagerAdapter mAdapter;
    private TabFragment[] mFragments = new TabFragment[mTitles.length];


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nestedscrolling);

        viewPager = findViewById(R.id.id_stickynavlayout_viewpager);
        tabLayout = findViewById(R.id.id_stickynavlayout_indicator);

        initDatas();
    }

    private void initDatas()
    {
        for (int i = 0; i < mTitles.length; i++)
        {
            mFragments[i] = (TabFragment) TabFragment.newInstance(mTitles[i]);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return mTitles.length;
            }

            @Override
            public Fragment getItem(int position)
            {
                return mFragments[position];
            }

        };

        viewPager.setAdapter(mAdapter);
        tabLayout.addTab(tabLayout.newTab().setText("简介"));
        tabLayout.addTab(tabLayout.newTab().setText("评价"));
        tabLayout.addTab(tabLayout.newTab().setText("相关"));
        tabLayout.setupWithViewPager(viewPager);
    }

}
