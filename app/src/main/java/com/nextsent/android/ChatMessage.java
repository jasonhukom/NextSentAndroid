package com.nextsent.android;

public class ChatMessage {
    private String from;
    private String text;
    private String time;
    private long created;

    public ChatMessage(String from, String text, String time, long created) {
        this.from = from;
        this.text = text;
        this.time = time;
        this.created = created;
    }

    public String getFrom() { return from; }
    public String getText() { return text; }
    public String getTime() { return time; }
    public long getCreated() { return created; }
}
