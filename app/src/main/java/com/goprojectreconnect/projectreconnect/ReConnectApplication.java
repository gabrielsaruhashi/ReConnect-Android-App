package com.goprojectreconnect.projectreconnect;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.goprojectreconnect.projectreconnect.Clients.FacebookClient;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
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
    private static ArrayList<Long> facebookFriendsIds;

    private static TreeSet facebookPermissionsSet;
    private static ParseUser currentUser;
    private static boolean mFirstLoad;


    @Override
    public void onCreate() {
        super.onCreate();

        // initialize boolean
        mFirstLoad = true;

        // Use for monitoring Parse network traffic
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        // set applicationId and server based on the values in the Heroku settings.
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("myAppId") // should correspond to APP_ID env variable
                .clientBuilder(builder)
                .clientKey(null)  // set explicitly unless clientKey is explicitly configured on Parse server
                .server("http://android-reconnect-server.herokuapp.com/parse").build());

        // instantiate parse-facebook
        ParseFacebookUtils.initialize(this);

        // test
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("SAVE_WORKED", "ay");
                } else {
                    e.printStackTrace();
                }
            }
        });


        // set context
        ReConnectApplication.context = this;
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


}

