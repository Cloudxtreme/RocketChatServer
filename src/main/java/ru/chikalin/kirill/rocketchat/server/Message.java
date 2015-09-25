package ru.chikalin.kirill.rocketchat.server;

import org.springframework.data.annotation.Id;

/**
 * Сообщение
 *
 * Date: 25/09/15
 *
 * @author Kirill Chikalin
 */
public class Message {
    @Id
    private String id;
    private String text;

    public Message() {
    }

    public Message(String text) {
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
}
