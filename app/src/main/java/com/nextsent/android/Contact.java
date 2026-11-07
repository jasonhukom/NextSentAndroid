package com.nextsent.android;

public class Contact {
    private String name;
    private String about;
    private String lastMessage;
    private String time;
    private String color;
    private boolean isGroup;

    public Contact(String name, String about, String lastMessage, String time, String color, boolean isGroup) {
        this.name = name;
        this.about = about;
        this.lastMessage = lastMessage;
        this.time = time;
        this.color = color;
        this.isGroup = isGroup;
    }

    public String getName() { return name; }
    public String getAbout() { return about; }
    public String getLastMessage() { return lastMessage; }
    public String getTime() { return time; }
    public String getColor() { return color; }
    public boolean isGroup() { return isGroup; }

    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }
    public void setTime(String time) { this.time = time; }
}
