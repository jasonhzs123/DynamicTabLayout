package com.demo.hzs.dynamictabdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DynamicTabLayout tabLayout;
    private String[] titles = {"首页", "消息"};
    private List<String> mImg_select = new ArrayList<>();
    private List<String> mImg_unselect = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private boolean hasImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.dynamicTabLayout);
        if (hasImg) {//带图片的tab
            mImg_unselect.add("http://pp14ek5dq.bkt.clouddn.com/cp_unselect.png");
            mImg_select.add("http://pp14ek5dq.bkt.clouddn.com/cp_select.png");
            mImg_unselect.add("http://pp14ek5dq.bkt.clouddn.com/chess_unselect.png");
            mImg_select.add("http://pp14ek5dq.bkt.clouddn.com/chess_select.png");
            tabLayout.setTabData(titles, mImg_select, mImg_unselect);
        } else {
            tabLayout.setTabData(titles);
        }
        for (String title : titles) {
            mFragments.add(SimpleCardFragment.getInstance("Switch Fragment " + title));
        }
        final ViewPager vp = findViewById(R.id.vp);
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp.setCurrentItem(position);
            }
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tabLayout.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
