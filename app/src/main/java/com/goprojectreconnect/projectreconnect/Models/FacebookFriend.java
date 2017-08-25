package com.goprojectreconnect.projectreconnect.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by GabrielSaruhashi on 16/08/17.
 */

public class FacebookFriend {
    private String name;
    private String imageUrl;
    private long fbUserID;

    // for retrieving facebook data
    public static FacebookFriend fromJSON(JSONObject json) {
        FacebookFriend fbFriend = new FacebookFriend();
        try {
            fbFriend.setFbUserID(json.getLong("id"));
            fbFriend.setName(json.getString("name"));
            JSONObject pictureData = json.getJSONObject("picture").getJSONObject("data");
            fbFriend.imageUrl = pictureData.getString("url");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fbFriend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getFbUserID() {
        return fbUserID;
    }

    public void setFbUserID(long fbUserID) {
        this.fbUserID = fbUserID;
    }
}
