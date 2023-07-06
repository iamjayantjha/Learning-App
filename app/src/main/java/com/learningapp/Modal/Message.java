package com.learningapp.Modal;

public class Message {
    String message, sender;
    public static String SENT_BY_USER = "user";
    public static String SENT_BY_BOT = "bot";

    public Message(String message, String sender) {
        this.sender = sender;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
