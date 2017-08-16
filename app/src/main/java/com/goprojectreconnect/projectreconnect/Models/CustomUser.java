package com.goprojectreconnect.projectreconnect.Models;

import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by GabrielSaruhashi on 14/08/17.
 */

public class CustomUser {

    private ParseUser user;


    public CustomUser(ParseUser obj) {
        user = obj;
    }

    public void setSomeString(String stringName, String value) {
        user.put(stringName, value);
        user.saveInBackground();
    }

    public void setSomeArrayList(String arrayName, ArrayList array) {
        user.put(arrayName, array);
        user.saveInBackground();
    }

}
