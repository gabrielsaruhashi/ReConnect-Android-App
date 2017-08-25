package com.goprojectreconnect.projectreconnect.Models;

/**
 * Created by GabrielSaruhashi on 16/08/17.
 */

public class User {
    private boolean role;
    private String userId;
    private String name;
    private String profilePictureUrl;
    private String essayAbout;


    /* GETTERS & SETTERS */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getEssayAbout() {
        return essayAbout;
    }

    public void setEssayAbout(String essayAbout) {
        this.essayAbout = essayAbout;
    }
}
