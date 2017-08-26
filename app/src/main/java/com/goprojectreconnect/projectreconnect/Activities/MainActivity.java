package com.goprojectreconnect.projectreconnect.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.goprojectreconnect.projectreconnect.Fragments.DialogFragments.InvitationDialogFragment;
import com.goprojectreconnect.projectreconnect.Fragments.MainAddReConnectionFragment;
import com.goprojectreconnect.projectreconnect.Fragments.MainHomeFragment;
import com.goprojectreconnect.projectreconnect.Fragments.MainInboxFragment;
import com.goprojectreconnect.projectreconnect.Fragments.MainMemoriesFragment;
import com.goprojectreconnect.projectreconnect.Fragments.MainProfileFragment;
import com.goprojectreconnect.projectreconnect.R;
import com.goprojectreconnect.projectreconnect.ReConnectApplication;
import com.parse.ParseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity {
    // UI References
    @BindView(R.id.sliding_tabs) TabLayout tabLayout;
    @BindView(R.id.container) ViewPager mViewPager;
    @BindView(R.id.toolbar) Toolbar toolbar;


    private int imageResId[] = new int[] {R.drawable.ic_home, R.drawable.ic_photo, R.drawable.ic_mail, R.drawable.ic_person, R.drawable.ic_group_add};
    private ParseUser currentUser;
    private MainFragmentPagerAdapter mMainFragmentPagerAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // enable vectors
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


        //setup toolbar
        setSupportActionBar(toolbar);


        // instantiate context
        context = this;
        currentUser = ReConnectApplication.getCurrentUser();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mMainFragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mMainFragmentPagerAdapter);
        // give the Tab Layout the ViewPager
        tabLayout.setupWithViewPager(mViewPager);

        // add imageview with profile picture on actionbar
        setupToolbar();

    }

    public void setupToolbar() {
        ImageView ivToolbarProfilePicture = (ImageView) findViewById(R.id.ivToolbarProfilePicture);
        TextView tvTitle = (TextView) findViewById(R.id.headerText);

        tvTitle.setText("ReConnect");

        Glide.with(context)
                .load(currentUser.getString("profile_image_url"))
                .bitmapTransform(new CropCircleTransformation(context))
                .into(ivToolbarProfilePicture);

        for (int i = 0; i < imageResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }

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

        private final int PAGE_COUNT = 5;
        private MainHomeFragment mainHomeFragment;
        private MainAddReConnectionFragment addReConnectionFragment;
        private MainMemoriesFragment mainMemoriesFragment;
        private MainInboxFragment mainInboxFragment;
        private MainProfileFragment mainProfileFragment;


        public MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0) {
                mainHomeFragment = getMainHomeInstance();
                return mainHomeFragment;
            } else if (position == 1) {
                mainMemoriesFragment = getMainMemoriesInstace();
                return mainMemoriesFragment;
            } else if (position == 2) {
                mainInboxFragment = getMainInboxInstance();
                return mainInboxFragment;
            } else if (position == 3) {
                mainProfileFragment = getMainProfileInstance();
                return mainProfileFragment;
            } else if (position == 4) {
                addReConnectionFragment = getAddReconnectionInstance();
                return addReConnectionFragment;
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

            return null;
        }

        private MainHomeFragment getMainHomeInstance() {
            return (mainHomeFragment == null)?
                    new MainHomeFragment() : mainHomeFragment;
        }
        private MainAddReConnectionFragment getAddReconnectionInstance() {
            return (addReConnectionFragment == null)?
                    new MainAddReConnectionFragment() : addReConnectionFragment;
        }

        private MainMemoriesFragment getMainMemoriesInstace() {
            return (mainMemoriesFragment == null)?
                    new MainMemoriesFragment() : mainMemoriesFragment;
        }

        private MainInboxFragment getMainInboxInstance() {
            return (mainInboxFragment == null)?
                    new MainInboxFragment() : mainInboxFragment;
        }

        private MainProfileFragment getMainProfileInstance() {
            return (mainProfileFragment == null)?
                    new MainProfileFragment() : mainProfileFragment;
        }
    }

    public void showInvitationDialog() {
        FragmentManager fm = getSupportFragmentManager();
        InvitationDialogFragment invitationDialogFragment = InvitationDialogFragment.newInstance();
        invitationDialogFragment.show(fm, "fragment_invitation_dialog_fragment");
    }

}
