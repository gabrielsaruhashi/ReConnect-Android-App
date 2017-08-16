package com.goprojectreconnect.projectreconnect.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.goprojectreconnect.projectreconnect.Clients.FacebookClient;
import com.goprojectreconnect.projectreconnect.Models.CustomUser;
import com.goprojectreconnect.projectreconnect.R;
import com.goprojectreconnect.projectreconnect.ReConnectApplication;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    // Facebook client and permissions for login
    List<String> permissions;
    FacebookClient client;
    ParseUser currentUser;

    // UI references.
    private LoginButton loginButton;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initiate client and context
        client = ReConnectApplication.getFacebookRestClient();
        context = this;
        currentUser = ReConnectApplication.getCurrentUser();

        // check if user is already authenticated
        if (currentUser != null && currentUser.isAuthenticated()) {
            onSuccessfulSignUp();

        }
        // create permissions
        permissions = Arrays.asList("user_friends");
        // facebook authentication
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("user_friends"));
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    // login method for facebook authentication
    public void login() {

        // login
        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
            // upon success, return user object
            @Override
            public void done(final ParseUser user, ParseException err) {

                if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                    LoginManager.getInstance().logOut();
                }
                // if user is registering
                else if (user.isNew()) {
                    user.getCurrentUser();

                    // get user info
                    client.getMyInfo(new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            JSONObject userJSON = response.getJSONObject();
                            // initialize properties for new user
                            try {
                                String name = userJSON.getString("name");
                                String profileImageUrl = userJSON.getJSONObject("picture")
                                        .getJSONObject("data")
                                        .getString("url");

                                CustomUser newCustomUser = new CustomUser(user);
                                newCustomUser.setSomeString("name", name);
                                newCustomUser.setSomeString("profile_image_url", profileImageUrl);
                                // start activity for context
                                Log.d("MyApp", "User signed up and logged in through Facebook!");
                                onSuccessfulSignUp();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    user.getCurrentUser();
                    user.saveInBackground();
                    Log.d("MyApp", "User logged in through Facebook!");
                    //TODO replace with OnLoginSuccess
                    onSuccessfulSignUp();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    public void onSuccessfulSignUp() {
        Intent i = new Intent(context, SignUpActivity.class);
        context.startActivity(i);
    }
    public void onLoginSuccess() {

        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
    }


    /*
    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    } */

    /**
     * Callback received when a permissions request has been completed.
     */

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    } */

}

