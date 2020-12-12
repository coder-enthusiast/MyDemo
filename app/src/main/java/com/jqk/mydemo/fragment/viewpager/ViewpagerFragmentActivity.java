package com.jqk.mydemo.fragment.viewpager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.jqk.mydemo.R;
import java.util.ArrayList;
import java.util.List;

public class ViewpagerFragmentActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;

    private Button first;
    private Button second;
    private Button third;
    private NoScrollViewPager viewPager;

    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_viewpager);

        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);
        viewPager = findViewById(R.id.viewPager);

        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);

        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();

        fragmentList.add(firstFragment);
        fragmentList.add(secondFragment);
        fragmentList.add(thirdFragment);

        viewPager.setAdapter(new MViewpagerAdapter(getSupportFragmentManager(), fragmentList));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.first:
                showFragment(FIRST);
                break;
            case R.id.second:
                showFragment(SECOND);
                break;
            case R.id.third:
                showFragment(THIRD);
                break;
        }
    }

    public void showFragment(int type) {
        viewPager.setCurrentItem(type, false);
    }
}
