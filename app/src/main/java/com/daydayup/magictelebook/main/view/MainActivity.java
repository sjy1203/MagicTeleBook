package com.daydayup.magictelebook.main.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daydayup.magictelebook.BaseAcitivity;
import com.daydayup.magictelebook.R;
import com.daydayup.magictelebook.main.adpter.ContactAdapter;
import com.daydayup.magictelebook.main.adpter.RecordAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseAcitivity implements IMainView{
    //view
    private ViewPager viewPager;
    private TabLayout tabLayout;

    //adapter
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //initial viewpager
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new RecordsFragment());
        fragmentList.add(new ContactsFragment());
        List<String> titleList = new ArrayList<>();
        titleList.add("通话记录");
        titleList.add("联系人");
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(viewPagerAdapter);

        //initial tablayout
        tabLayout.addTab(tabLayout.newTab().setText("通话记录"));
        tabLayout.addTab(tabLayout.newTab().setText("联系人"));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    //TODO:@yanhangyu
    @Override
    public RecordAdapter getRecordAdapter() {
        return null;
    }
    //TODO:@yanhangyu
    @Override
    public ContactAdapter getContactAdapter() {
        return null;
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> fragmentList;
        private List<String> titleList;
        public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList,List<String> titleList) {
            super(fm);
            this.fragmentList = fragmentList;
            this.titleList = titleList;

        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
