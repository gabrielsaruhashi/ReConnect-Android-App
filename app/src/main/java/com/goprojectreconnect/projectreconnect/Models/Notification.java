package com.goprojectreconnect.projectreconnect.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by gabrielsaruhashi on 8/25/17.
 */

@ParseClassName("Notification")
public class Notification extends ParseObject {
    private int notificationType;
    private ParseUser sender;
    private ParseUser recipient;
    private String notificationImageUrl;
    private String recipientName;
    private String message;

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationImageUrl() {
        return getString("notification_image_url");
    }

    public void setNotificationImageUrl(String notificationImageUrl) {
        this.notificationImageUrl = notificationImageUrl;
        put("notification_image_url", notificationImageUrl);
    }

    public String getRecipientName() {
        return getString("recipient_name");
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
        put("recipient_name", recipientName);
    }

    public String getMessage() {
        return getString("message");
    }

    public void setMessage(String message) {
        this.message = message;
        put("message", message);
    }

    public ParseUser getRecipient() {
        return getParseUser("recipient");
    }

    public void setRecipient(ParseUser recipient) {
        this.recipient = recipient;
        put("recipient", recipient);
    }

    public ParseUser getSender() {
        return getParseUser("sender");
    }

    public void setSender(ParseUser sender) {
        this.sender = sender;
        put("sender", sender);
    }
}
