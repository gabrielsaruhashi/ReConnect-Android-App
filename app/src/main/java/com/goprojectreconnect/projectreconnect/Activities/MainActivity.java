package com.goprojectreconnect.projectreconnect.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.goprojectreconnect.projectreconnect.Fragments.MainAddReConnectionFragment;
import com.goprojectreconnect.projectreconnect.Fragments.MainMemoriesFragment;
import com.goprojectreconnect.projectreconnect.R;
import com.parse.ParseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    // UI References
    @BindView(R.id.sliding_tabs) TabLayout tabLayout;
    @BindView(R.id.container) ViewPager mViewPager;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private MainFragmentPagerAdapter mMainFragmentPagerAdapter;
    private MainMemoriesFragment mainMemoriesFragment;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // enable vectors
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        // setup toolbar
        setSupportActionBar(toolbar);
        // instantiate context
        context = this;

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mMainFragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mMainFragmentPagerAdapter);
        // give the Tab Layout the ViewPager
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            //TODO remove this

            LoginManager.getInstance().logOut();
            ParseUser.logOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            context.startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     FragmentPagerAdapter for the main activity
     */
    public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

        private String tabTitles[] = new String[] { "Feed", "ReConnect", "Memories" };
        private int imageResId[] = new int[] {R.drawable.ic_home, R.drawable.ic_group_add, R.drawable.ic_photo};
        private final int PAGE_COUNT = 3;
        private MainAddReConnectionFragment addReConnectionFragment;
        private MainMemoriesFragment mainMemoriesFragment;


        public MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0) {
                addReConnectionFragment = getAddReconnectionInstance();
                return addReConnectionFragment;
            } else if (position == 1) {
                mainMemoriesFragment = getMainMemoriesInstace();
                return mainMemoriesFragment;
            } else {
                return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            Drawable image = ContextCompat.getDrawable(context, imageResId[position]);
            //Drawable image = context.getResources().getDrawable(imageResId[position]);
            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            // Replace blank spaces with image icon
            SpannableString sb = new SpannableString("   " + tabTitles[position]);
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }

        private MainAddReConnectionFragment getAddReconnectionInstance() {
            return (addReConnectionFragment == null)?
                    new MainAddReConnectionFragment() : addReConnectionFragment;
        }

        private MainMemoriesFragment getMainMemoriesInstace() {
            return (mainMemoriesFragment == null)?
                    new MainMemoriesFragment() : mainMemoriesFragment;
        }
    }

}
