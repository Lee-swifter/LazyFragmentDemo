package lic.swifter.demo.lazy;

import android.os.Bundle;
import androidx.annotation.CheckResult;
import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import lic.swifter.demo.lazy.fragment.FirstFragment;
import lic.swifter.demo.lazy.fragment.FourthFragment;
import lic.swifter.demo.lazy.fragment.LazyFragment;
import lic.swifter.demo.lazy.fragment.SecondFragment;
import lic.swifter.demo.lazy.fragment.ThirdFragment;
import lic.swifter.demo.lazy.widget.FragmentTab;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout tabsWrapper;

    private List<LazyFragment> fragmentList;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = $(R.id.main_toolbar);
        viewPager = $(R.id.main_view_pager);
        tabsWrapper = $(R.id.bottom_wrapper);
        FragmentTab firstTab = $(R.id.fragment_tab_first);
        FragmentTab secondTab = $(R.id.fragment_tab_second);
        FragmentTab thirdTab = $(R.id.fragment_tab_third);
        FragmentTab fourthTab = $(R.id.fragment_tab_fourth);

        toolbar.setTitle(R.string.app_name);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            setSupportActionBar(toolbar);

        fragmentList = new ArrayList<>();
        fragmentList.add(new FirstFragment());
        fragmentList.add(new SecondFragment());
        fragmentList.add(new ThirdFragment());
        fragmentList.add(new FourthFragment());

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new MainPager(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new FragmentChangeListener());

        firstTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        secondTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        thirdTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });
        fourthTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });
    }

    private void changeBottomTab(int position) {
        if (currentPosition == position)
            return;

        tabsWrapper.getChildAt(currentPosition).setSelected(false);
        currentPosition = position;
        tabsWrapper.getChildAt(currentPosition).setSelected(true);

        Log.i("lazy_load", "Main Bottom Tab selected "+position);
    }


    @SuppressWarnings("unchecked")
    @CheckResult
    public <T extends View> T $(@IdRes int id) {
        return (T) findViewById(id);
    }

    private class MainPager extends FragmentPagerAdapter {

        MainPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    private class FragmentChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            changeBottomTab(position);

            Log.i("lazy_load", "Main ViewPager selected "+position);
        }
    }
}
