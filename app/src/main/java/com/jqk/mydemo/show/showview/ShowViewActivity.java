package com.jqk.mydemo.show.showview;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.github.chrisbanes.photoview.PhotoView;
import com.jqk.commonlibrary.util.StatusBarUtil;
import com.jqk.mydemo.R;

import java.util.ArrayList;
import java.util.List;

public class ShowViewActivity extends BaseActivity {

    private RelativeLayout contentView;
    private FrameLayout frameLayout;
    private View view, bg;

    private ViewPager viewPager;

    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private List<Integer> datas;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.immersive(this);
        setContentView(R.layout.activity_showview);

        recyclerView = findViewById(R.id.recyclerView);
        frameLayout = findViewById(R.id.frameLayout);
        contentView = findViewById(R.id.contentView);

        datas = new ArrayList<Integer>();
        datas.add(R.drawable.icon_bird);
        datas.add(R.drawable.icon_bird);
        datas.add(R.drawable.icon_bird);
        datas.add(R.drawable.icon_bird);
        datas.add(R.drawable.icon_bird);
        datas.add(R.drawable.icon_bird);
        datas.add(R.drawable.icon_bird);
        datas.add(R.drawable.icon_bird);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(this, datas);
        myRecyclerViewAdapter.setOnClickListener(new MyRecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(int x, int y, int width, int height) {
                showView.showAnim(x, 0, y, 0,
                        width, height);
            }
        });
        recyclerView.setAdapter(myRecyclerViewAdapter);

        showView = new ShowView(this);
        showView.setActivity(this);
        view = LayoutInflater.from(this).inflate(R.layout.layout_viewpager, null, false);
        bg = LayoutInflater.from(this).inflate(R.layout.layout_bg, null, false);
        showView.setBgView(bg);
        try {
            showView.setContentView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        frameLayout.addView(showView);
        viewPager = view.findViewById(R.id.viewPager);

        final List<View> viewList = new ArrayList<View>();
        for (int i = 0; i < datas.size(); i++) {
            PhotoView photoView = new PhotoView(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setLayoutParams(lp);
            photoView.setImageResource(R.drawable.icon_bird);
            viewList.add(photoView);
        }
        showView.setPhotoView((PhotoView) viewList.get(0));
        MyViewPagerAdapter myAdapter = new MyViewPagerAdapter(viewList);
        viewPager.setAdapter(myAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("123", "onPageSelected");
                showView.setPhotoView((PhotoView) viewList.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);

        StatusBarUtil.setPaddingSmart(this, contentView);
    }
}
