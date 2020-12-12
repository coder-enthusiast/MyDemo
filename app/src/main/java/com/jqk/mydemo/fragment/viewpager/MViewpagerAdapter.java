package com.jqk.mydemo.fragment.viewpager;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MViewpagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> mfragmentList;

	public MViewpagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
		super(fm);
		this.mfragmentList = fragmentList;
	}

	@Override
	public Fragment getItem(int position) {
		return mfragmentList.get(position);
	}

	@Override
	public int getCount() {
		return mfragmentList.size();
	}

}
