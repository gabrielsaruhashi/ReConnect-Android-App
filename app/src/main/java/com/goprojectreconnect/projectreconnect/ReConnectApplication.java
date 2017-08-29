package com.goprojectreconnect.projectreconnect;

import android.app.Application;
import android.content.Context;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.maps.model.LatLng;
import com.goprojectreconnect.projectreconnect.Clients.FacebookClient;
import com.goprojectreconnect.projectreconnect.Models.FacebookFriend;
import com.goprojectreconnect.projectreconnect.Models.Notification;
import com.goprojectreconnect.projectreconnect.Models.Reconnection;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by GabrielSaruhashi on 14/08/17.
 */

public class ReConnectApplication extends Application {

    // application context
    private static Context context;
    private static FacebookClient facebookClient;
    private static ArrayList<FacebookFriend> facebookFriends;
    private static TreeSet facebookPermissionsSet;
    private static ParseUser currentUser;
    private static boolean mFirstLoad;
    private static final LatLng PARIS_LATLNG = new LatLng(48.8566,2.3522);
    private static final ArrayList<LatLng> RECONNECT_CHAPTERS = new ArrayList<LatLng>(Arrays.asList(PARIS_LATLNG));


    @Override
    public void onCreate() {
        super.onCreate();

        // initialize boolean and instantiate reConnectChapters
        mFirstLoad = true;

        // Use for monitoring Parse network traffic
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        // register Parse classes
        ParseObject.registerSubclass(Reconnection.class);
        ParseObject.registerSubclass(Notification.class);

        // set applicationId and server based on the values in the Heroku settings.
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("myAppId") // should correspond to APP_ID env variable
                .clientBuilder(builder)
                .clientKey(null)  // set explicitly unless clientKey is explicitly configured on Parse server
                .server("http://android-reconnect-server.herokuapp.com/parse").build());

        // instantiate parse-facebook
        ParseFacebookUtils.initialize(this);

        // set context
        ReConnectApplication.context = this;

        // call setup functions
        getCurrentUser();
        getFacebookFriends();
    }


    // facebook client singleton
    public static FacebookClient getFacebookRestClient() {
        if (facebookClient == null) {
            facebookClient = new FacebookClient(context);
        }
        return facebookClient;
    }

    public static ParseUser getCurrentUser() {
        if (currentUser == null) {
            currentUser = ParseUser.getCurrentUser();
        }

        return currentUser;
    }

    public static ArrayList<FacebookFriend> getFacebookFriends() {

        if (mFirstLoad && currentUser != null && currentUser.isAuthenticated()) {
            facebookFriends = new ArrayList<>();
            // start a new thread to execute the runnable codeblock
            Thread thread = new Thread( ) {
                @Override
                public void run() {

                    // the code to execute when the runnable is processed by a thread
                    FacebookClient client = ReConnectApplication.getFacebookRestClient();

                    client.getFriendsUsingApp(new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            // gets friends ids
                            try {
                                JSONArray friends = response.getJSONObject().getJSONArray("data");
                                for (int i = 0; i < friends.length(); i++) {
                                    FacebookFriend friend = FacebookFriend.fromJSON(friends.getJSONObject(i));
                                    facebookFriends.add(friend);
                                }
                                mFirstLoad = false;

                            } catch(JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            };

            // start thread
            thread.start();
            // set first load to false for future getFacebookFriends() call

            // wait for the thread to return the facebook API request
            try {
                thread.join(0);
            } catch (InterruptedException i) {
                i.getMessage();
            }

        }
        // return your fb friends' ids
        return facebookFriends;
    }

    public static TreeSet getFacebookPermissionsSet() {
        // upon the first load, get all permissions user gave us
        if (mFirstLoad) {
            // get my permissions from facebook
            facebookClient.getMyPermissions(new GraphRequest.Callback() {
                // create a set with the names of all permissions
                @Override
                public void onCompleted(GraphResponse response) {
                    try {
                        JSONArray permissions = response.getJSONObject().getJSONArray("data");
                        for (int i = 0; i < permissions.length(); i ++) {
                            String permissionName = permissions.getJSONObject(i).getString("permission");
                            String status = permissions.getJSONObject(i).getString("status");
                            // add all permissions to global set of permissions
                            if (status.equals("granted")) {
                                facebookPermissionsSet.add(permissionName);
                            }
                        }
                    } catch (JSONException e) {
                        e.getMessage();
                    }
                }
            });
        }

        return facebookPermissionsSet;
    }

    public static ArrayList<LatLng> getReConnectChapters() {
        return RECONNECT_CHAPTERS;
    }
}

