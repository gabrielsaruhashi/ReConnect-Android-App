package com.goprojectreconnect.projectreconnect.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by gabrielsaruhashi on 8/29/17.
 */

@ParseClassName("Message")
public class Message extends ParseObject {
    private ParseUser sender;
    private String body;
    private String reconnectionId;


    public ParseUser getSender() {
        return getParseUser("sender");
    }

    public void setSender(ParseUser sender) {
        this.sender = sender;
        put("sender", sender);
    }

    public String getBody() {
        return getString("body");
    }

    public void setBody(String body) {
        this.body = body;
        put("body", body);
    }

    public String getReconnectionId() {
        return getString("reconnection_id");
    }

    public void setReconnectionId(String reconnectionId) {
        this.reconnectionId = reconnectionId;
        put("reconnection_id", reconnectionId);
    }
}
