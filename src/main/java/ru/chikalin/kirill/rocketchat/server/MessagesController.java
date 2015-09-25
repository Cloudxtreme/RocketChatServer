package ru.chikalin.kirill.rocketchat.server;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/sendPhoto", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Message> sendPhoto(@RequestParam(required = false, defaultValue = "guest") String username,
                                      @RequestParam(required = true) MultipartFile photo) throws IOException {
        byte[] bytes = photo.getBytes();
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        GridFsOperations gridOperations =
                (GridFsOperations) ctx.getBean("gridFsTemplate");
        GridFSFile file = gridOperations.store(new ByteArrayInputStream(bytes), UUID.randomUUID().toString());
        Message message = new Message();
        message.setPhoto(file.get("_id").toString());
        message.setUsername(username);
        message.setDate(new Date());
        repository.save(message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/files/{file_id}", method = RequestMethod.GET)
    public void getFile(@PathVariable("file_id") String file_id, HttpServletResponse response) {
        if (file_id.isEmpty()) {
            throw new IllegalArgumentException();
        }
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        GridFsOperations gridOperations =
                (GridFsOperations) ctx.getBean("gridFsTemplate");
        GridFSDBFile file = gridOperations.findOne(Query.query(Criteria.where("_id").is(file_id)));
        InputStream inputStream = file.getInputStream();
        try {
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }
}
