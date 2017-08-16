package com.goprojectreconnect.projectreconnect.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.goprojectreconnect.projectreconnect.Fragments.SignUpBasicInfoFragment;
import com.goprojectreconnect.projectreconnect.Fragments.SignUpEssaysFragment;
import com.goprojectreconnect.projectreconnect.Fragments.SignUpVideo;
import com.goprojectreconnect.projectreconnect.R;
import com.goprojectreconnect.projectreconnect.ReConnectApplication;
import com.parse.ParseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class SignUpActivity extends AppCompatActivity implements SignUpBasicInfoFragment.OnNextClickedListener, SignUpEssaysFragment.OnNextClickedListener {

    private ParseUser currentUser;
    private Context context;
    public static int currentViewpagerItem;

    // UI References
    @BindView(R.id.viewPager) ViewPager mViewPager;
    SectionsPagerAdapter mPageAdapter;
    @BindView(R.id.indicator) CircleIndicator indicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        // instantiate context and current user
        context = this;
        currentUser = ReConnectApplication.getCurrentUser();

        // set adapter for viewpager
        mPageAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPageAdapter);
        indicator.setViewPager(mViewPager);

        // instantiate currentViewpagerItem support variable
        currentViewpagerItem = mViewPager.getCurrentItem();

    }

    @Override
    public void updateViewpager() {
        mViewPager.setCurrentItem(currentViewpagerItem + 1);
        // update counter
        currentViewpagerItem += 1;

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 3;

        private SignUpBasicInfoFragment signUpBasicInfoFragment;
        private SignUpEssaysFragment signUpEssaysFragment;
        private SignUpVideo signUpVideo;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                signUpBasicInfoFragment = new SignUpBasicInfoFragment();
                return signUpBasicInfoFragment;
            } if (position == 1) {
                signUpEssaysFragment = new SignUpEssaysFragment();
                return signUpEssaysFragment;
            } if (position == 2) {
                signUpVideo = new SignUpVideo();
                return signUpVideo;
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Basic Info";
                case 1:
                    return "Essays";
                case 2:
                    return "Video";
            }
            return null;
        }

    }

}
