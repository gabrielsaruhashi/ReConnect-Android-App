package com.goprojectreconnect.projectreconnect.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by gabrielsaruhashi on 8/25/17.
 */

@ParseClassName("Notification")
public class Notification extends ParseObject {
    private int notificationType;
    private String recipientId;
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

    public String getRecipientId() {
        return getString("recipient_id");
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
        put("recipient_id", recipientId);
    }
}
