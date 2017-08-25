package com.goprojectreconnect.projectreconnect.Clients;

import android.content.Context;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;

/**
 * Created by GabrielSaruhashi on 14/08/17.
 */

public class FacebookClient {
    Context context;

    public FacebookClient(Context context) {
        this.context = context;
    }

    public void getFriendsUsingApp(GraphRequest.Callback callback) {
        Bundle params = new Bundle();
        params.putString("fields", "id,name,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/friends", params, HttpMethod.GET,
                callback).executeAndWait();
    }

    public void getMyInfo(GraphRequest.Callback callback) {
        Bundle params = new Bundle();
        params.putString("fields", "id,name,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me", params, HttpMethod.GET,
                callback).executeAsync();
    }

    public void getMyPermissions(GraphRequest.Callback callback) {
        Bundle params = new Bundle();
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions", params, HttpMethod.GET,
                callback).executeAsync();
    }

}
