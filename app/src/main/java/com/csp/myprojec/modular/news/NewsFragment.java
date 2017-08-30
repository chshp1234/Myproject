package com.csp.myprojec.modular.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csp.myprojec.R;
import com.csp.myprojec.base.Config;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by CSP on 2017/1/19.
 */

public class NewsFragment extends Fragment {

    @BindView(R.id.viewpagertab)
    SmartTabLayout viewpagertab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    Unbinder unbinder;
    NewsListFragment newsListFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, null);
        unbinder = ButterKnife.bind(this, view);
//        viewpager.setOffscreenPageLimit();
        init();
        return view;
    }

    private void init() {
        newsListFragment = new NewsListFragment();
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());//fragment+fragment时要用getChildFragmentManager()
        adapter.addFragment(newsListFragment.newInstance(Config.MASTER), "大师");
        adapter.addFragment(newsListFragment.newInstance(Config.TRAVEL), "旅行");
        adapter.addFragment(newsListFragment.newInstance(Config.COSMIC_SPACE), "宇宙");
        adapter.addFragment(newsListFragment.newInstance(Config.CULTURAL), "人文");
        adapter.addFragment(newsListFragment.newInstance(Config.OCEAN), "海洋");
        adapter.addFragment(newsListFragment.newInstance(Config.NATURAL_DISASTER), "自然灾害");
        adapter.addFragment(newsListFragment.newInstance(Config.PHOTOGRAPHY), "摄影");
        adapter.addFragment(newsListFragment.newInstance(Config.KNOWLEDGE_OF_ANIMALS), "动物");
        viewpager.setAdapter(adapter);
        viewpagertab.setViewPager(viewpager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        newsListFragment.mvpPresenter.detachView();
        unbinder.unbind();
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments = new ArrayList<>();
        private List<String> mFragmentTitles = new ArrayList<>();

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
