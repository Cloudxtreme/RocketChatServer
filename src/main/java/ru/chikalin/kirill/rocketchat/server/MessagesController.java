package ru.chikalin.kirill.rocketchat.server;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessagesController {

    @Autowired
    private MessageRepository repository;

    private static final String template = "Hello, %s!";

    @RequestMapping("/getMessages")
    public Message getMessages(@RequestParam(value = "username", defaultValue = "guest") String username) {
        return new Message(String.format(template, username));
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public ResponseEntity<Message> sendMessage(@RequestParam(required = false) String username, @RequestParam(required = true) String text) throws IllegalArgumentException {
        return new ResponseEntity<Message>(new Message(text), HttpStatus.OK);
    }
}
