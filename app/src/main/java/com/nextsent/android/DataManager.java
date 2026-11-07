package com.nextsent.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {
    private static DataManager instance;

    private String currentAccount = "Jason";
    private Map<String, String> accountAbouts = new HashMap<>();
    private List<Contact> seedContacts = new ArrayList<>();
    private Map<String, List<ChatMessage>> chatThreads = new HashMap<>();

    private DataManager() {
        accountAbouts.put("Jason", "Available");
        accountAbouts.put("Jason Alt", "Hey there! I am using NextSent.");

        seedContacts.add(new Contact("Alex Morgan", "At work", "See you later!", "09:42", "#547C91", false));
        seedContacts.add(new Contact("Maya Chen", "Can't talk, WhatsApp only", "That sounds great", "Yesterday", "#AD776F", false));
        seedContacts.add(new Contact("Noah Williams", "Available", "📷 Photo", "Yesterday", "#718B65", false));
        seedContacts.add(new Contact("Olivia Parker", "Busy", "Send me the link", "Mon", "#9A7AAB", false));
        seedContacts.add(new Contact("Ethan Brooks", "At the gym", "Voice message", "Sun", "#BA8D55", false));
        seedContacts.add(new Contact("Sofia Reyes", "Available", "See you tomorrow", "Sat", "#BD7188", false));
        seedContacts.add(new Contact("Daniel Kim", "On the phone", "👍", "Fri", "#6D829E", false));
        seedContacts.add(new Contact("Ava Thompson", "Available", "Location shared", "Thu", "#8B9870", false));
    }

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public String getCurrentAccount() { return currentAccount; }
    public void setCurrentAccount(String account) {
        this.currentAccount = account;
        if (!accountAbouts.containsKey(account)) {
            accountAbouts.put(account, "Available");
        }
    }

    public Map<String, String> getAccountAbouts() { return accountAbouts; }
    public String getAccountAbout(String account) {
        if (accountAbouts.containsKey(account)) {
            return accountAbouts.get(account);
        }
        return "Available";
    }

    public void setAccountAbout(String account, String about) {
        accountAbouts.put(account, about);
    }

    public List<Contact> getContacts() { return seedContacts; }

    public void addGroup(String groupName, String participants) {
        Contact group = new Contact(groupName, participants, "Group created", "Now", "#008F83", true);
        seedContacts.add(0, group);
        addMessage(groupName, "Group created with " + participants);
    }

    public List<ChatMessage> getThread(String contactName) {
        if (!chatThreads.containsKey(contactName)) {
            chatThreads.put(contactName, new ArrayList<>());
        }
        return chatThreads.get(contactName);
    }

    public void addMessage(String contactName, String text) {
        List<ChatMessage> list = getThread(contactName);
        String timeStr = java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT).format(new java.util.Date());
        list.add(new ChatMessage(currentAccount, text, timeStr, System.currentTimeMillis()));

        for (Contact c : seedContacts) {
            if (c.getName().equals(contactName)) {
                c.setLastMessage(text);
                c.setTime(timeStr);
                break;
            }
        }
    }
}
