package com.example.simpletodo;

public class ListItem {
    String text;
    int prio;

    public ListItem(String text, int prio){
        this.text = text;
        this.prio = prio;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPrio() {
        return prio;
    }

    @Override
    public String toString() {
        return text;
    }
}
