package com.example.simpletodo;

public class ListItem {
    String text;
    String prio;

    public ListItem(String text, String prio){
        this.text = text;
        this.prio = prio;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPrio() {
        return String.valueOf(prio);
    }

    @Override
    public String toString() {
        return text;
    }
}
