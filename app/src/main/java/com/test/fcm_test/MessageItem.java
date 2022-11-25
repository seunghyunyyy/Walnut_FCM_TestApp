package com.test.fcm_test;

public class MessageItem {
    private String icon;
    private String title;
    private String body;

    public MessageItem(String icon, String title, String body) {
        this.icon = icon;
        this.title = title;
        this.body = body;
    }

    public String getIcon() {
        return this.icon;
    }
    public String getTitle() {
        return this.title;
    }
    public String getBody() {
        return this.body;
    }
}

