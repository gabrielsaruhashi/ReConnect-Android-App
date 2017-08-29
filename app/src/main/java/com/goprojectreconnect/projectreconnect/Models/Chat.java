package com.goprojectreconnect.projectreconnect.Models;

/**
 * Created by gabrielsaruhashi on 8/25/17.
 */

public class Chat {
    private String name;
    private String lastMessage;
    private String timestamp;
    private String profilePictureUrl;
    private String reconnectionId;

    public static Chat fromReconnectionObject(Reconnection reconnection) {
        Chat chat = new Chat();
        chat.setName(reconnection.getHost().getString("name"));
        chat.setProfilePictureUrl(reconnection.getHost().getString("profile_image_url"));
        chat.setReconnectionId(reconnection.getObjectId());

        if (reconnection.getLastMessage() == null) {
            chat.setLastMessage("You have just ReConnected :) !");
            chat.setTimestamp("Now");
        }


        return chat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getReconnectionId() {
        return reconnectionId;
    }

    public void setReconnectionId(String reconnectionId) {
        this.reconnectionId = reconnectionId;
    }
}
