package com.example.seoul;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class TabPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> items = new ArrayList<>();
    int _numOfTabs;

    public TabPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this._numOfTabs = numOfTabs;
    }

    public void addItem(Fragment item){
        items.add(item);
    }
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return _numOfTabs;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}