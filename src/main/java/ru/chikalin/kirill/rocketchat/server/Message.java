package ru.chikalin.kirill.rocketchat.server;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Сообщение
 *
 * Date: 25/09/15
 *
 * @author Kirill Chikalin
 */
@Document
public class Message {
    @Id
    private String id;
    private String text;
    private String username;
    private Date date;
    private Location location;

    public Message() {
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public void setUsername(String chat) {
        this.username = chat;
    }

    public String getUsername() {
        return username;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
