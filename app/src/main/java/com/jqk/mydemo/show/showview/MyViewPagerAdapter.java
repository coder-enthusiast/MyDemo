package com.jqk.mydemo.show.showview;

import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {

    private List<View> views;

    public MyViewPagerAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0==arg1;
    }
    //有多少个切换页
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return views.size();
    }

    //对超出范围的资源进行销毁
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        // TODO Auto-generated method stub
        container.removeView(views.get(position));
    }
    //对显示的资源进行初始化
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        container.addView(views.get(position));
        return views.get(position);
    }
}
