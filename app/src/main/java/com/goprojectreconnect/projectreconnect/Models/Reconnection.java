package com.goprojectreconnect.projectreconnect.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by gabrielsaruhashi on 8/28/17.
 */

@ParseClassName("Reconnection")
public class Reconnection extends ParseObject {
    private ParseUser refugee;
    private ParseUser host;
    private int interactionIndex;
    private String lastMessage;

    // Ensure that your subclass has a public default constructor
    public Reconnection() {
        super();
    }
    public Reconnection(ParseUser refugee, ParseUser host) {
        setRefugee(refugee);
        setHost(host);
        // instantiate message count
        setInteractionIndex(0);
    }

    public ParseUser getRefugee() {
        return getParseUser("refugee");
    }

    public void setRefugee(ParseUser refugee) {
        this.refugee = refugee;
        put("refugee", refugee);
    }

    public ParseUser getHost() {
        return getParseUser("host");
    }

    public void setHost(ParseUser host) {
        this.host = host;
        put("host", host);
    }

    public int getInteractionIndex() {
        return getInt("interaction_index");
    }

    public void setInteractionIndex(int interactionIndex) {
        this.interactionIndex = interactionIndex;
        put("interaction_index", interactionIndex);
    }

    public String getLastMessage() {
        return getString("last_message");
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
        put("last_message", lastMessage);
    }
}
