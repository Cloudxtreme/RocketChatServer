package ru.chikalin.kirill.rocketchat.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class MessagesController {

    @Autowired
    private MessageRepository repository;

    @RequestMapping("/getMessages")
    public List<Message> getMessages(@RequestParam(value = "username", defaultValue = "guest") String username) {
        return repository.findByUsername(username);
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public ResponseEntity<Message> sendMessage(@RequestParam(required = false, defaultValue = "guest") String username,
                                               @RequestParam(required = true) String text) throws IllegalArgumentException {
        Message message = new Message();
        message.setText(text);
        message.setUsername(username);
        message.setDate(new Date());
        repository.save(message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/sendLocation", method = RequestMethod.POST)
    public ResponseEntity<Message> sendLocation(@RequestParam(required = false, defaultValue = "guest") String username,
                                               @RequestParam(required = true) float latitude,
                                               @RequestParam(required = true) float longitude) throws IllegalArgumentException {
        Message message = new Message();
        message.setLocation(new Location(latitude, longitude));
        message.setUsername(username);
        message.setDate(new Date());
        repository.save(message);
        return new ResponseEntity<>(new Message(), HttpStatus.OK);
    }
}
