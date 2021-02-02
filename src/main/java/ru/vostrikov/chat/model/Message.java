package ru.vostrikov.chat.model;

import java.util.Date;

public class Message {

    private String author;
    private String message;
    private Date date;

    public Message() {
    }

    public Message(String author, String message, Date date) {
        this.author = author;
        this.message = message;
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
