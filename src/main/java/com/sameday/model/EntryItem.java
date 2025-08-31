package com.sameday.model;

import com.sameday.helper.MoodHelper;

public class EntryItem {
    private int id;
    private String date;
    private String content;
    private int moodId;

    public EntryItem(int id, String date, String content, int moodId) {
        this.id = id;
        this.date = date;
        this.content = content;
        this.moodId = moodId;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public int getMoodId() {
        return moodId;
    }

    @Override
    public String toString() {
        String mood = MoodHelper.getEmoji(moodId);
        String summary = content.length() > 30 ? content.substring(0, 30) + "..." : content;
        return mood + "  " + date + " - " + summary;
    }
}
